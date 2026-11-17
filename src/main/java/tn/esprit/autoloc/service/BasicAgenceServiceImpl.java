package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BasicAgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository repository;
    private final IEmployeService employeService;

    public BasicAgenceServiceImpl(IAgenceRepository repository, IEmployeService employeService) {
        this.repository = repository;
        this.employeService = employeService;
    }

    @Override
    public boolean check(Agence agence, boolean isNew) {
        return Utils.isNotEmpty(agence.getNom()) && Utils.isNotEmpty(agence.getTelephone());
    }

    @Override
    public Agence create(Agence agence) {
        if (agence.getIdAgence() != null) {
            throw new IllegalArgumentException("identifiant non null");
        }
        if (!check(agence, true)) {
            throw new IllegalArgumentException("le nom ou tel non null");
        }
        return repository.save(agence);
    }

    @Override
    public Agence findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence not found with id ", id.toString()));
    }

    @Override
    public List<Agence> findAll() {
        return repository.findAll();
    }

    @Override
    public Agence update(Long id, Agence agence) {
        Agence existing = findById(id);
        if (!check(agence, false)) {
            throw new IllegalArgumentException("Agence invalide");
        }
        existing.setNom(agence.getNom());
        existing.setVille(agence.getVille());
        existing.setAdresse(agence.getAdresse());
        existing.setTelephone(agence.getTelephone());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Agence not found with id ", id.toString());
        }
        repository.deleteById(id);
    }

    @Override
    public boolean hasVehicules(Long id) {
        return false;
    }

    @Override
    @Transactional
    public Agence updateWithEmployes(Long id, Agence source) {
        Set<Employe> nouveaux = new HashSet<>(source.getEmployes());
        Agence agence = update(id, source);
        for (Employe employe : nouveaux) {
            agence.addEmploye(employe);
            employeService.create(employe);
        }
        agence.setEffectif(nouveaux.size());
        return repository.save(agence);
    }
}

package tn.esprit.autoloc.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.util.HashSet;
import java.util.Set;

@Primary
@Service
public class AgenceServiceImpl extends GenericServiceImpl<Agence, Long>
        implements IAgenceService, ISimulationService {

    private final IVehiculeService vehiculeService;
    private final IEmployeService employeService;

    public AgenceServiceImpl(IAgenceRepository repository, IVehiculeService vehiculeService,
                             IEmployeService employeService) {
        super(repository);
        this.vehiculeService = vehiculeService;
        this.employeService = employeService;
    }

    @Override
    public boolean check(Agence agence, boolean isNew) {
        return Utils.isNotEmpty(agence.getNom()) && Utils.isNotEmpty(agence.getTelephone());
    }

    @Override
    protected String getEntityName() {
        return "Agence";
    }

    @Override
    protected Long getId(Agence agence) {
        return agence.getIdAgence();
    }

    @Override
    protected void setFields(Agence source, Agence target) {
        target.setNom(source.getNom());
        target.setVille(source.getVille());
        target.setAdresse(source.getAdresse());
        target.setTelephone(source.getTelephone());
    }

    @Override
    public boolean hasVehicules(Long id) {
        return !findById(id).getVehicules().isEmpty();
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

    @Override
    public String simuler(Agence agence, int nbJours, VehiculeOffre offre) {
        StringBuilder rapport = new StringBuilder();
        rapport.append("Id: ").append(agence.getIdAgence())
                .append(", Nom: ").append(agence.getNom())
                .append(", Tel: ").append(agence.getTelephone()).append("\n");
        double total = 0;
        for (Vehicule vehicule : agence.getVehicules()) {
            total += vehiculeService.calculTarif(vehicule, nbJours, offre);
        }
        rapport.append("Total: ").append(total);
        return rapport.toString();
    }
}

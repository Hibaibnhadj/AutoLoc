package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.calcul.IOffreStrategy;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.Map;

@Service
public class VehiculeServiceImpl extends GenericServiceImpl<Vehicule, Long> implements IVehiculeService {
    private final Map<String, IOffreStrategy> strategies;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;

    public VehiculeServiceImpl(IVehiculeRepository repository, Map<String, IOffreStrategy> strategies,
                               IAgenceRepository agenceRepository,
                               IEquipementRepository equipementRepository) {
        super(repository);
        this.strategies = strategies;
        this.agenceRepository = agenceRepository;
        this.equipementRepository = equipementRepository;
    }

    @Override
    protected String getEntityName() {
        return "Vehicule";
    }

    @Override
    protected Long getId(Vehicule vehicule) {
        return vehicule.getIdVehicule();
    }

    @Override
    protected void setFields(Vehicule source, Vehicule target) {
        target.setImmatriculation(source.getImmatriculation());
        target.setMarque(source.getMarque());
        target.setModele(source.getModele());
        target.setCategorie(source.getCategorie());
        target.setTarifJournalier(source.getTarifJournalier());
        target.setStatut(source.getStatut());
        target.setAgence(source.getAgence());
    }

    @Override
    public boolean check(Vehicule vehicule, boolean isNew) {
        return Utils.isNotEmpty(vehicule.getImmatriculation())
                && Utils.isNotEmpty(vehicule.getMarque())
                && Utils.isNotEmpty(vehicule.getModele());
    }

    @Override
    public double calculTarif(Vehicule vehicule, int jours, VehiculeOffre offre) {
        double tarifJour = vehicule.getTarifJournalier().doubleValue();
        return strategies.get(offre.name()).calculer(tarifJour, jours);
    }

    @Override
    public Vehicule disaffecte(Long id) {
        Vehicule vehicule = findById(id);
        vehicule.setAgence(null);
        return repository.save(vehicule);
    }

    @Override
    public Vehicule affecte(Long idVehicule, Long idAgence) {
        Vehicule vehicule = findById(idVehicule);
        vehicule.setAgence(agenceRepository.findById(idAgence).orElseThrow(() ->
                new ResourceNotFoundException("Agence not found with id ", idAgence.toString())));
        return repository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule affecteEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = findById(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement).orElseThrow(() ->
                new ResourceNotFoundException("Equipement not found with id ", idEquipement.toString()));
        equipement.getVehicules().add(vehicule);   // owner side
        equipementRepository.save(equipement);
        return vehicule;
    }
}
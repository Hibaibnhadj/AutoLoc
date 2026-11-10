package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.calcul.IOffreStrategy;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.Map;

@Service
public class VehiculeServiceImpl extends GenericServiceImpl<Vehicule, Long> implements IVehiculeService {
    private final Map<String, IOffreStrategy> strategies;

    public VehiculeServiceImpl(IVehiculeRepository repository, Map<String, IOffreStrategy> strategies) {
        super(repository);
        this.strategies = strategies;
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
        var vehicule =findById(id);
        vehicule.setAgence(null);
        return repository.save(vehicule );
    }
}

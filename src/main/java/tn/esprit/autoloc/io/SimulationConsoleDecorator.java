package tn.esprit.autoloc.io;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.ISimulationService;

@Service
public class SimulationConsoleDecorator implements ISimulationService {
    private final ISimulationService delegate;
    private final RapportWriterFactory factory;

    public SimulationConsoleDecorator(@Qualifier("agenceServiceImpl") ISimulationService delegate,
                                      RapportWriterFactory factory) {
        this.delegate = delegate;
        this.factory = factory;
    }

    @Override
    public String simuler(Agence agence, int nbJours, VehiculeOffre offre) {
        String rapport = delegate.simuler(agence, nbJours, offre);
        String[] lignes = rapport.split("\n", 2);
        return factory.creer("CONSOLE").ecrire(lignes[0], lignes[1]);
    }
}

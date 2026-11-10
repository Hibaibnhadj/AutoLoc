package tn.esprit.autoloc.calcul;

import org.springframework.stereotype.Component;

@Component("WEEKEND")
public class WeekendOffre implements IOffreStrategy {
    @Override
    public double calculer(double tarifJour, int jours) {
        return tarifJour * 0.80 * jours;
    }
}

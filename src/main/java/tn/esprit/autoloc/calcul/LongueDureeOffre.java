package tn.esprit.autoloc.calcul;

import org.springframework.stereotype.Component;

@Component("LONGUE_DUREE")
public class LongueDureeOffre implements IOffreStrategy {
    @Override
    public double calculer(double tarifJour, int jours) {
        double total = tarifJour * 0.75 * jours;
        if (total > 2000) {
            total = total * 0.95;
        }
        return total;
    }
}

package tn.esprit.autoloc.io;

public class TexteRapportWriter implements IRapportWriter {
    @Override
    public String ecrire(String entete, String total) {
        return entete + "\n" + total;
    }
}

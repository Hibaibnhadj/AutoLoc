package tn.esprit.autoloc.io;

public class ConsoleRapportWriter implements IRapportWriter {
    @Override
    public String ecrire(String entete, String total) {
        System.out.println(entete);
        return total;
    }
}

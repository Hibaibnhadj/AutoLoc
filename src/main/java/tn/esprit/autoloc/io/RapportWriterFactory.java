package tn.esprit.autoloc.io;

import org.springframework.stereotype.Component;

@Component
public class RapportWriterFactory {
    public IRapportWriter creer(String type) {
        if ("CONSOLE".equals(type)) {
            return new ConsoleRapportWriter();
        }
        return new TexteRapportWriter();
    }
}

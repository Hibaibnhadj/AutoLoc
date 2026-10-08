package tn.esprit.autoloc;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.fail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IAgenceRepository;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

public class AgenceTests {
    @Autowired
    private AgenceRepositoryMock basicAgenceRepository;
    @Autowired
    private IAgenceRepository fullAgenceRepository;
    private void addAgence(CrudRepository<Agence, Long> repository) {

        Agence agence = new Agence();
        agence.setAdresse("1 Rue Hedi");
        agence.setNom("Agence ariana");
        agence.setTelephone("71585874");
        agence.setVille("Tunis");

        int unique = (int) (System.currentTimeMillis() % 100000);

        Vehicule v1 = new Vehicule();
        v1.setCategorie(CategorieVehicule.SUV);
        v1.setImmatriculation("TU96-" + unique);
        v1.setMarque("Isuzu");
        v1.setModele("DMax");
        v1.setStatut(StatutVehicule.MAINTENANCE);
        v1.setTarifJournalier(new BigDecimal("100"));
        v1.setAgence(agence);

        Vehicule v2 = new Vehicule();
        v2.setCategorie(CategorieVehicule.UTILITAIRE);
        v2.setImmatriculation("TU95-" + unique);
        v2.setMarque("Toyota");
        v2.setModele("Yaris");
        v2.setStatut(StatutVehicule.DISPONIBLE);
        v2.setTarifJournalier(new BigDecimal("80"));
        v2.setAgence(agence);

        Set<Vehicule> vehicules = new HashSet<>();
        vehicules.add(v1);
        vehicules.add(v2);
        agence.setVehicules(vehicules);

        repository.save(agence);
    }
    @Test
    public void basicAddAgence() {
        addAgence(basicAgenceRepository);
    }

    @Test
    public void fullAddAgence() {
        addAgence(fullAgenceRepository);
    }

    private void loadAgence(CrudRepository<Agence, Long> repository, String repositoryType) {
        Iterable<Agence> agences = repository.findAll();
        StringBuilder sb = new StringBuilder("\n");
        sb.append("Repository type: ").append(repositoryType).append("\n\n");

        for (Agence a : agences) {
            sb.append("Agence id: ").append(a.getIdAgence()).append("\n")
                    .append("Nom: ").append(a.getNom()).append("\n")
                    .append("Nombre de vehicules: ").append(a.getVehicules().size()).append("\n");

            for (Vehicule v : a.getVehicules()) {
                sb.append("  - Vehicule id: ").append(v.getIdVehicule())
                        .append(" | Immatriculation: ").append(v.getImmatriculation())
                        .append("\n");
            }
            sb.append("\n");
        }

        fail(sb.toString());
    }
    @Test
    public void loadPagedAgences() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "idAgence"));
        Page<Agence> page = fullAgenceRepository.findAll(pageable);

        StringBuilder sb = new StringBuilder("\n");
        sb.append("Total pages: ").append(page.getTotalPages()).append("\n")
                .append("Page courante: ").append(page.getNumber()).append("\n\n");

        for (Agence a : page.getContent()) {
            sb.append("Agence id: ").append(a.getIdAgence()).append("\n")
                    .append("Nom: ").append(a.getNom()).append("\n\n");
        }

        fail(sb.toString());
    }


    @Test
    public void basicLoadAgence() {
        loadAgence(basicAgenceRepository, "basic (CrudRepository)");
    }

    @Test
    public void fullLoadAgence() {
        loadAgence(fullAgenceRepository, "full (JpaRepository)");
    }
    @Test
    public void loadSortedAgences() {
        Iterable<Agence> agences = fullAgenceRepository.findAll(Sort.by(Sort.Direction.DESC, "idAgence"));
        StringBuilder sb = new StringBuilder("\n");

        for (Agence a : agences) {
            sb.append("Agence id: ").append(a.getIdAgence()).append("\n")
                    .append("Nom: ").append(a.getNom()).append("\n\n");
        }

        fail(sb.toString());
    }
}
interface AgenceRepositoryMock extends CrudRepository<Agence, Long> {


}
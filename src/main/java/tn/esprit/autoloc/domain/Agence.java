package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "agence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String ville;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int effectif;

    @OneToMany(mappedBy = "agence", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Vehicule> Vehicules;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    private Set<Employe> employes = new HashSet<>();

    public void addEmploye(Employe e) {
        employes.add(e);
        e.setAgence(this);
    }

    public void removeEmploye(Employe e) {
        employes.remove(e);
        e.setAgence(null);
    }
}

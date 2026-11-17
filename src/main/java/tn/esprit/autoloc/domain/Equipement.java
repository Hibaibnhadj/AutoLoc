package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "equipement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipement;

    @Column(nullable = false)
    private String libelle;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "vehicule_equipement",
            joinColumns = @JoinColumn(name = "id_equipement"),
            inverseJoinColumns = @JoinColumn(name = "id_vehicule"))
    private Set<Vehicule> vehicules = new HashSet<>();
}
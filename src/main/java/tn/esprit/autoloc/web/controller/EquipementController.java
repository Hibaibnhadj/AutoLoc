package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.service.IEquipementService;

import java.util.List;

@Tag(name = "Equipements", description = "Gestion des equipements")
@RestController
@RequestMapping("equipements")
@RequiredArgsConstructor
public class EquipementController {
    private final IEquipementService service;

    @Operation(summary = "Lister les equipements")
    @ApiResponse(responseCode = "200", description = "Liste des equipements")
    @GetMapping
    public List<Equipement> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Consulter un(e) equipement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipement trouvé(e)"),
            @ApiResponse(responseCode = "404", description = "Equipement introuvable")
    })
    @GetMapping("{id}")
    public Equipement findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Ajouter un(e) equipement")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Equipement créé(e)"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou identifiant renseigné")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Equipement create(@RequestBody Equipement equipement) {
        return service.create(equipement);
    }

    @Operation(summary = "Modifier un(e) equipement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Equipement modifié(e)"),
            @ApiResponse(responseCode = "404", description = "Equipement introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PutMapping("{id}")
    public Equipement update(@Parameter(description = "Identifiant") @PathVariable Long id,
                         @RequestBody Equipement equipement) {
        return service.update(id, equipement);
    }

    @Operation(summary = "Supprimer un(e) equipement")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Equipement supprimé(e)"),
            @ApiResponse(responseCode = "404", description = "Equipement introuvable")
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        service.deleteById(id);
    }
}


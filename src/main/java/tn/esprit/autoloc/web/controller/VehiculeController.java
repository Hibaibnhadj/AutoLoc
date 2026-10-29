package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@Tag(name = "Vehicules", description = "Gestion des vehicules")
@RestController
@RequestMapping("vehicules")
@RequiredArgsConstructor
public class VehiculeController {
    private final IVehiculeService service;

    @Operation(summary = "Lister les vehicules")
    @ApiResponse(responseCode = "200", description = "Liste des vehicules")
    @GetMapping
    public List<Vehicule> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Consulter un(e) vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicule trouvé(e)"),
            @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    })
    @GetMapping("{id}")
    public Vehicule findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Ajouter un(e) vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Vehicule créé(e)"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou identifiant renseigné")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return service.create(vehicule);
    }

    @Operation(summary = "Modifier un(e) vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicule modifié(e)"),
            @ApiResponse(responseCode = "404", description = "Vehicule introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PutMapping("{id}")
    public Vehicule update(@Parameter(description = "Identifiant") @PathVariable Long id,
                         @RequestBody Vehicule vehicule) {
        return service.update(id, vehicule);
    }

    @Operation(summary = "Supprimer un(e) vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Vehicule supprimé(e)"),
            @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        service.deleteById(id);
    }
}


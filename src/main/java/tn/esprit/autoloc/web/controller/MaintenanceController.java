package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.service.IMaintenanceService;

import java.util.List;

@Tag(name = "Maintenances", description = "Gestion des maintenances")
@RestController
@RequestMapping("maintenances")
@RequiredArgsConstructor
public class MaintenanceController {
    private final IMaintenanceService service;

    @Operation(summary = "Lister les maintenances")
    @ApiResponse(responseCode = "200", description = "Liste des maintenances")
    @GetMapping
    public List<Maintenance> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Consulter un(e) maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance trouvé(e)"),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    })
    @GetMapping("{id}")
    public Maintenance findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Ajouter un(e) maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Maintenance créé(e)"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou identifiant renseigné")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Maintenance create(@RequestBody Maintenance maintenance) {
        return service.create(maintenance);
    }

    @Operation(summary = "Modifier un(e) maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance modifié(e)"),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PutMapping("{id}")
    public Maintenance update(@Parameter(description = "Identifiant") @PathVariable Long id,
                         @RequestBody Maintenance maintenance) {
        return service.update(id, maintenance);
    }

    @Operation(summary = "Supprimer un(e) maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Maintenance supprimé(e)"),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        service.deleteById(id);
    }
}


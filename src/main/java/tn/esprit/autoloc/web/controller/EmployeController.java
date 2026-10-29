package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.IEmployeService;

import java.util.List;

@Tag(name = "Employes", description = "Gestion des employes")
@RestController
@RequestMapping("employes")
@RequiredArgsConstructor
public class EmployeController {
    private final IEmployeService service;

    @Operation(summary = "Lister les employes")
    @ApiResponse(responseCode = "200", description = "Liste des employes")
    @GetMapping
    public List<Employe> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Consulter un(e) employe")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employe trouvé(e)"),
            @ApiResponse(responseCode = "404", description = "Employe introuvable")
    })
    @GetMapping("{id}")
    public Employe findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Ajouter un(e) employe")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employe créé(e)"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou identifiant renseigné")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employe create(@RequestBody Employe employe) {
        return service.create(employe);
    }

    @Operation(summary = "Modifier un(e) employe")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employe modifié(e)"),
            @ApiResponse(responseCode = "404", description = "Employe introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PutMapping("{id}")
    public Employe update(@Parameter(description = "Identifiant") @PathVariable Long id,
                         @RequestBody Employe employe) {
        return service.update(id, employe);
    }

    @Operation(summary = "Supprimer un(e) employe")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Employe supprimé(e)"),
            @ApiResponse(responseCode = "404", description = "Employe introuvable")
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        service.deleteById(id);
    }
}


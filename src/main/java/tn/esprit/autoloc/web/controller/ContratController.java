package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.IContratService;

import java.util.List;

@Tag(name = "Contrats", description = "Gestion des contrats")
@RestController
@RequestMapping("contrats")
@RequiredArgsConstructor
public class ContratController {
    private final IContratService service;

    @Operation(summary = "Lister les contrats")
    @ApiResponse(responseCode = "200", description = "Liste des contrats")
    @GetMapping
    public List<Contrat> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Consulter un(e) contrat")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contrat trouvé(e)"),
            @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    })
    @GetMapping("{id}")
    public Contrat findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Ajouter un(e) contrat")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Contrat créé(e)"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou identifiant renseigné")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contrat create(@RequestBody Contrat contrat) {
        return service.create(contrat);
    }

    @Operation(summary = "Modifier un(e) contrat")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contrat modifié(e)"),
            @ApiResponse(responseCode = "404", description = "Contrat introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PutMapping("{id}")
    public Contrat update(@Parameter(description = "Identifiant") @PathVariable Long id,
                         @RequestBody Contrat contrat) {
        return service.update(id, contrat);
    }

    @Operation(summary = "Supprimer un(e) contrat")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Contrat supprimé(e)"),
            @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        service.deleteById(id);
    }
}


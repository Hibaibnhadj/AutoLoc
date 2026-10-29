package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.service.IReservationService;

import java.util.List;

@Tag(name = "Reservations", description = "Gestion des reservations")
@RestController
@RequestMapping("reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final IReservationService service;

    @Operation(summary = "Lister les reservations")
    @ApiResponse(responseCode = "200", description = "Liste des reservations")
    @GetMapping
    public List<Reservation> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Consulter un(e) reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation trouvé(e)"),
            @ApiResponse(responseCode = "404", description = "Reservation introuvable")
    })
    @GetMapping("{id}")
    public Reservation findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Ajouter un(e) reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reservation créé(e)"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou identifiant renseigné")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation create(@RequestBody Reservation reservation) {
        return service.create(reservation);
    }

    @Operation(summary = "Modifier un(e) reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation modifié(e)"),
            @ApiResponse(responseCode = "404", description = "Reservation introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PutMapping("{id}")
    public Reservation update(@Parameter(description = "Identifiant") @PathVariable Long id,
                         @RequestBody Reservation reservation) {
        return service.update(id, reservation);
    }

    @Operation(summary = "Supprimer un(e) reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reservation supprimé(e)"),
            @ApiResponse(responseCode = "404", description = "Reservation introuvable")
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        service.deleteById(id);
    }
}


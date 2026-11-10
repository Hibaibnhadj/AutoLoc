package tn.esprit.autoloc.web.controller;

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

    @GetMapping
    public List<Vehicule> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public Vehicule findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return service.create(vehicule);
    }

    @PutMapping("{id}")
    public Vehicule update(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        return service.update(id, vehicule);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("{id}/desaffecter")
    public Vehicule disaffecte(@PathVariable Long id) {
        return service.disaffecte(id);
    }
}
package com.openclassrooms.P3_Full_Stack_portail_locataire.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.openclassrooms.P3_Full_Stack_portail_locataire.services.RentalService;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.Rental;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rental")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<List<Rental>> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(rentals);
    }

    @PostMapping
    public ResponseEntity<Rental> createRental(@Valid @RequestBody Rental rental) {
        Rental savedRental = rentalService.saveRental(rental);
        return ResponseEntity.ok(savedRental);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Long id, @Valid @RequestBody Rental rentalDetails) {
//        System.out.println("ID: " + id + ", Rental Details: " + rentalDetails);

        return rentalService.getRentalById(id)
                .map(existingRental -> {
                    if (rentalDetails.getName() != null) {
                        existingRental.setName(rentalDetails.getName());
                    }
                    if (rentalDetails.getSurface() != null) {
                        existingRental.setSurface(rentalDetails.getSurface());
                    }
                    if (rentalDetails.getPrice() != null) {
                        existingRental.setPrice(rentalDetails.getPrice());
                    }
                    if (rentalDetails.getPicture() != null) {
                        existingRental.setPicture(rentalDetails.getPicture());
                    }
                    if (rentalDetails.getDescription() != null) {
                        existingRental.setDescription(rentalDetails.getDescription());
                    }
                    if (rentalDetails.getOwner() != null) {
                        existingRental.setOwner(rentalDetails.getOwner());
                    }

                    // Sauvegarde et retour de la réponse
                    Rental updatedRental = rentalService.saveRental(existingRental);
                    return ResponseEntity.ok(updatedRental); // 200 OK avec l'objet mis à jour
                })
                .orElse(ResponseEntity.notFound().build()); // 404 si l'entité n'existe pas
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id)
                .map(ResponseEntity::ok) // Retourne 200 OK si trouvé
                .orElse(ResponseEntity.notFound().build()); // Retourne 404 si non trouvé
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Test OK!");
    }
}
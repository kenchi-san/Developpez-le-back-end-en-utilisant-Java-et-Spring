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
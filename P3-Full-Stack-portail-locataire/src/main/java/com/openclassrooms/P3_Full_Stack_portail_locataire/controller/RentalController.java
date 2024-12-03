package com.openclassrooms.P3_Full_Stack_portail_locataire.controller;

import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.AddRentalDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.AllInfoRentalDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.EditRentalDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;
import com.openclassrooms.P3_Full_Stack_portail_locataire.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.openclassrooms.P3_Full_Stack_portail_locataire.service.RentalService;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.Rental;

import jakarta.validation.Valid;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rental")
public class RentalController {

    private final RentalService rentalService;
    private final UserService userService;

    public RentalController(RentalService rentalService, UserService userService) {
        this.rentalService = rentalService;
        this.userService = userService;
    }

    //TODO ok
    @GetMapping("/list")
    public ResponseEntity<List<AllInfoRentalDto>> getAllRentals() {
        List<AllInfoRentalDto> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(rentals);
    }

    @PostMapping("/add")
    public ResponseEntity<Rental> createRental(@Valid @RequestBody AddRentalDto rentalDto) {
        // Récupérer l'email de l'utilisateur connecté depuis le SecurityContext
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Trouver l'utilisateur connecté par email
        User owner = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable avec l'email : " + email));

        // Créer une nouvelle entité Rental à partir du DTO
        Rental newRental = new Rental();
        newRental.setName(rentalDto.getName());
        newRental.setDescription(rentalDto.getDescription());
        newRental.setPicture(rentalDto.getPicture());
        newRental.setPrice(rentalDto.getPrice());
        newRental.setSurface(rentalDto.getSurface());
        newRental.setOwner(owner); // Associer l'utilisateur connecté comme propriétaire

        // Sauvegarder la location dans la base de données
        Rental savedRental = rentalService.saveRental(newRental);

        // Générer l'URL de la ressource créée et l'ajouter dans l'en-tête Location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRental.getId())
                .toUri();

        // Retourner une réponse avec le statut 201 Created
        return ResponseEntity.created(location).body(savedRental);
    }

    //TODO ok
    @PutMapping("/edit/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Long id, @Valid @RequestBody EditRentalDto rentalDetails) {
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
// Sauvegarder l'entité mise à jour dans la base de données
                    Rental updatedRental = rentalService.saveRental(existingRental);
// Exclure les messages de la réponse (en les mettant à null)
                    updatedRental.setMessages(null);


                    return ResponseEntity.ok(updatedRental); // 200 OK avec l'objet mis à jour
                })
                .orElse(ResponseEntity.notFound().build()); // 404 si l'entité n'existe pas
    }

    @GetMapping("detail/{id}")
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


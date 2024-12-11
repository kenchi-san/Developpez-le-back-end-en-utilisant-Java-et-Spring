package com.openclassrooms.P3_Full_Stack_portail_locataire.controller;

import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.*;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;
import com.openclassrooms.P3_Full_Stack_portail_locataire.service.ImageService;
import com.openclassrooms.P3_Full_Stack_portail_locataire.service.MessageService;
import com.openclassrooms.P3_Full_Stack_portail_locataire.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.openclassrooms.P3_Full_Stack_portail_locataire.service.RentalService;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.Rental;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final UserService userService;
    private final ImageService imageService;
    private final MessageService messageService;

    public RentalController(RentalService rentalService, UserService userService, ImageService imageService, MessageService messageService) {
        this.rentalService = rentalService;
        this.userService = userService;
        this.imageService = imageService;
        this.messageService = messageService;
    }

    @Operation(
            summary = "Lister toutes les locations",
            description = "Cet endpoint renvoie une liste de toutes les locations avec leurs informations principales, comme le nom, la surface, le prix, et d'autres détails."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des locations récupérée avec succès",
                    content = @Content(schema = @Schema(implementation = AllInfoRentalDto.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping()
    public ResponseEntity<Map<String, List<AllInfoRentalDto>>> getAllRentals() {
        // Récupérer toutes les locations depuis le service
        List<AllInfoRentalDto> rentals = rentalService.getAllRentals();
//System.out.println(rentals);
        // Créer une map avec la clé "rentals" et la liste des locations
        Map<String, List<AllInfoRentalDto>> response = new HashMap<>();
        response.put("rentals", rentals);

        // Retourner la réponse sous la forme d'un objet JSON
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Ajouter une nouvelle location", description = "Permet d'ajouter une nouvelle location en fournissant les détails nécessaires")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Location créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur de validation dans les données fournies"),
            @ApiResponse(responseCode = "403", description = "Vous devez être connecté")
    })
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Rental> createRental(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Les données de la location à ajouter",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AddRentalDto.class),
                            examples = @ExampleObject(
                                    name = "Exemple de location",
                                    value = """
                                            {
                                              "name": "Appartement T3",
                                              "surface": 75.5,
                                              "price": 1200.50,
                                              "description": "Bel appartement en centre-ville avec balcon",
                                              "picture": "https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg"
                                            }
                                            """
                            )
                    )
            )
            @Valid @ModelAttribute AddRentalDto rentalDto // Notez @ModelAttribute
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User owner = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable avec l'email : " + email));


        MultipartFile picture = rentalDto.getPicture();

        // Traiter et enregistrer l'image
        String imageUrl = imageService.savePicture(picture);

        Rental newRental = new Rental();
        newRental.setName(rentalDto.getName());
        newRental.setDescription(rentalDto.getDescription());
        newRental.setPicture(imageUrl);
        newRental.setPrice(rentalDto.getPrice());
        newRental.setSurface(rentalDto.getSurface());
        newRental.setOwner(owner);
        newRental.setCreatedAt(LocalDateTime.now());
        Rental savedRental = rentalService.saveRental(newRental);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRental.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedRental);
    }

    @Operation(
            summary = "Modifier une location existante",
            description = "Met à jour les détails d'une location identifiée par son ID avec les nouvelles informations fournies."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Location mise à jour avec succès",
                    content = @Content(
                            schema = @Schema(implementation = Rental.class),
                            examples = @ExampleObject(
                                    name = "Exemple de réponse réussie",
                                    value = """
                                            {
                                              "name": "Appartement T3 rénové",
                                              "surface": 85.5,
                                              "price": 1300.00,
                                              "description": "Appartement rénové avec balcon",
                                              "picture": "https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Location introuvable avec l'ID fourni",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides fournies dans la requête",
                    content = @Content
            )
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Rental> updateRental(
            @Parameter(description = "Identifiant de la location à modifier", example = "1")
            @PathVariable Long id,
//            @RequestPart(value = "rentalDetails")
            @Valid EditRentalDto rentalDetails
    ) {
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
                    if (rentalDetails.getDescription() != null) {
                        existingRental.setDescription(rentalDetails.getDescription());
                    }
                    existingRental.setUpdatedAt(LocalDateTime.now());

                    // Sauvegarder l'entité mise à jour dans la base de données
                    Rental updatedRental = rentalService.saveRental(existingRental);
                    return ResponseEntity.ok(updatedRental); // 200 OK avec l'objet mis à jour
                })
                .orElse(ResponseEntity.notFound().build()); // 404 si l'entité n'existe pas
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailRentalDto> getRentalById(@PathVariable Long id) {
        Optional<Rental> rental = rentalService.getRentalById(id);

        if (rental.isPresent()) {
            Rental foundRental = rental.get();
            // Création du DTO pour le Rental
            DetailRentalDto rentalDto = new DetailRentalDto(
                    foundRental.getId(),
                    foundRental.getName(),
                    foundRental.getSurface(),
                    foundRental.getPrice(),
                    foundRental.getPicture(),
                    foundRental.getCreatedAt(),
                    foundRental.getUpdatedAt(),
                    foundRental.getDescription(),
                    foundRental.getOwner()
            );
            System.out.println("ID: " + id + ", Rental Details: " + rentalDto);

            return ResponseEntity.ok(rentalDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


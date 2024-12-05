package com.openclassrooms.P3_Full_Stack_portail_locataire.controller;

import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.AddRentalDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.AllInfoRentalDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.DetailRentalDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.EditRentalDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;
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

    @Operation(
            summary = "Lister toutes les locations",
            description = "Cet endpoint renvoie une liste de toutes les locations avec leurs informations principales, comme le nom, la surface, le prix, et d'autres détails."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des locations récupérée avec succès",
                    content = @Content(schema = @Schema(implementation = AllInfoRentalDto.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/list")
    public ResponseEntity<List<AllInfoRentalDto>> getAllRentals() {
        List<AllInfoRentalDto> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(rentals);
    }

    @Operation(summary = "Ajouter une nouvelle location", description = "Permet d'ajouter une nouvelle location en fournissant les détails nécessaires")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Location créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur de validation dans les données fournies"),
            @ApiResponse(responseCode = "403", description = "Vous devez être connecté")
    })
    @PostMapping("/add")
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
            @Valid @RequestBody AddRentalDto rentalDto
    )
    {
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
    @PutMapping("/edit/{id}")
    public ResponseEntity<Rental> updateRental(
            @Parameter(
                    description = "Identifiant de la location à modifier",
                    example = "1"
            ) @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Les détails mis à jour de la location",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = EditRentalDto.class),
                            examples = @ExampleObject(
                                    name = "Exemple de données de requête",
                                    value = """
                {
                  "name": "Appartement T3 rénové",
                  "surface": 85.5,
                  "price": 1300.00,
                  "description": "Appartement rénové avec balcon et cuisine moderne",
                  "picture": "https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg"
                }
                """
                            )
                    )
            ) @Valid @RequestBody EditRentalDto rentalDetails
    ) {
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
    public ResponseEntity<DetailRentalDto> getRentalById(@PathVariable Long id) {
        return rentalService.getDetailRentalById(id)
                .map(ResponseEntity::ok) // Retourne 200 OK avec le DTO
                .orElse(ResponseEntity.notFound().build()); // Retourne 404 si non trouvé
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Test OK!");
    }
}


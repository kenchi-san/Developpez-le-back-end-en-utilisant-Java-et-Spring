package com.openclassrooms.P3_Full_Stack_portail_locataire.controller;

import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.CreateMessageDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.UpdateMessageDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.Message;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.Rental;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;
import com.openclassrooms.P3_Full_Stack_portail_locataire.service.MessageService;
import com.openclassrooms.P3_Full_Stack_portail_locataire.service.RentalService;
import com.openclassrooms.P3_Full_Stack_portail_locataire.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final RentalService rentalService;
    private final UserService userService;

    public MessageController(MessageService messageService, RentalService rentalService, UserService userService) {
        this.messageService = messageService;
        this.rentalService = rentalService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> addMessage(@Valid @RequestBody CreateMessageDto createMessageDto) {
        try {
            // Obtenez l'utilisateur connecté
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Utilisateur non authentifié."));
            }

            String email = authentication.getName();
            User user = userService.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));

            // Récupérez le rental correspondant
            Rental rental = rentalService.getRentalById(createMessageDto.getRentalId())
                    .orElseThrow(() -> new IllegalArgumentException("Location non trouvée avec l'ID : " + createMessageDto.getRentalId()));

            // Créez et associez le message
            Message message = new Message();
            message.setMessage(createMessageDto.getMessage());
            message.setUser(user);
            message.setRental(rental);
            message.setCreatedAt(LocalDateTime.now());



            messageService.save(message);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Message ajouté avec succès !"));
        } catch (UsernameNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Une erreur interne est survenue."));
        }
    }
}

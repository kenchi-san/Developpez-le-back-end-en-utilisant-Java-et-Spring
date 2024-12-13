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

    @PostMapping("/{rentalId}")
    public ResponseEntity<String> addMessage(@PathVariable Long rentalId,@Valid @RequestBody CreateMessageDto createMessageDto) {

        // Obtenez l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié.");
        }

        String email = authentication.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));

        // Récupérez le rental correspondant
        Rental rental = rentalService.getRentalById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Rental non trouvé avec l'ID : " + rentalId));

        // Créez et associez le message
        Message message = new Message();
        message.setMessage(createMessageDto.getMessage());
        message.setUser(user);
        message.setRental(rental);

        // Sauvegardez le message
        messageService.save(message);

        return ResponseEntity.status(HttpStatus.CREATED).body("Message ajouté avec succès !");
    }
}

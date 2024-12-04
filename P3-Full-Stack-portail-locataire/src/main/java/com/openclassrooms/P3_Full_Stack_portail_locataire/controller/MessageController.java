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
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final RentalService rentalService;
    private final UserService userService;

    public MessageController(MessageService messageService, RentalService rentalService, UserService userService) {
        this.messageService = messageService;
        this.rentalService = rentalService;
        this.userService = userService;
    }

    @PostMapping("/add/{rentalId}")
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

    /**
     * Éditer un message attaché à un rental.
     */
    @PutMapping("rental/{rentalId}/edit/{messageId}")
    public ResponseEntity<String> editMessage(
            @PathVariable Long rentalId,
            @PathVariable Long messageId,
            @Valid @RequestBody UpdateMessageDto updateMessageDto) {

        // Obtenir l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié.");
        }

        String email = authentication.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));

        // Vérifier que le rental existe
        Rental rental = rentalService.getRentalById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Rental non trouvé avec l'ID : " + rentalId));

        // Vérifier que le message existe
        Message message = messageService.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message non trouvé avec l'ID : " + messageId));

        // Vérifier que le message est attaché au rental donné
        if (!message.getRental().getId().equals(rental.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ce message n'est pas associé à ce rental.");
        }

        // Vérifier si l'utilisateur connecté est le propriétaire du message
        if (!message.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vous n'êtes pas autorisé à modifier ce message.");
        }

        // Mettre à jour le contenu du message
        message.setMessage(updateMessageDto.getMessage());
        messageService.save(message);

        return ResponseEntity.ok("Message modifié avec succès !");
    }

    /**
     * Supprimer un message attaché à un rental.
     */
    @DeleteMapping("rental/{rentalId}/delete/{messageId}")
    public ResponseEntity<String> deleteMessage(
            @PathVariable Long rentalId,
            @PathVariable Long messageId) {

        // Obtenir l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié.");
        }

        String email = authentication.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));

        // Vérifier que le rental existe
        Rental rental = rentalService.getRentalById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Rental non trouvé avec l'ID : " + rentalId));

        // Vérifier que le message existe
        Message message = messageService.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message non trouvé avec l'ID : " + messageId));

        // Vérifier que le message est attaché au rental donné
        if (!message.getRental().getId().equals(rental.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ce message n'est pas associé à ce rental.");
        }

        // Vérifier si l'utilisateur connecté est le propriétaire du message
        if (!message.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vous n'êtes pas autorisé à supprimer ce message.");
        }

        // Supprimer le message
        messageService.delete(message);

        return ResponseEntity.ok("Message supprimé avec succès !");
    }

}

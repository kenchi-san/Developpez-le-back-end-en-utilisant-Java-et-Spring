package com.openclassrooms.P3_Full_Stack_portail_locataire.service;

import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.DetailRentalDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.MessageDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.AllInfoRentalDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.Message;
import com.openclassrooms.P3_Full_Stack_portail_locataire.repository.MessageRepository;
import org.springframework.stereotype.Service;
import com.openclassrooms.P3_Full_Stack_portail_locataire.repository.RentalRepository;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.Rental;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final MessageRepository messageRepository;

    public RentalService(RentalRepository rentalRepository, MessageRepository messageRepository) {
        this.rentalRepository = rentalRepository;
        this.messageRepository = messageRepository;
    }


    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }
    public Optional<DetailRentalDto> getDetailRentalById(Long id) {
        // Récupérer le Rental par son id
        return rentalRepository.findById(id)
                .map(rental -> {
                    // Récupérer les messages associés au Rental
                    List<Message> messages = messageRepository.findByRentalId(rental.getId());

                    // Convertir les messages en MessageDto
                    List<MessageDto> messageDtos = toMessageDTOList(messages);

                    // Créer un DetailRentalDto
                    return new DetailRentalDto(
                            rental.getId(),
                            rental.getName(),
                            rental.getSurface(),
                            rental.getPrice(),
                            rental.getPicture(),
                            messageDtos
                    );
                });
    }
    public List<AllInfoRentalDto> getAllRentals() {
        // Récupérer toutes les locations
        List<Rental> rentals = rentalRepository.findAll();

        // Pour chaque location, récupérer les messages associés et créer un DTO avec la liste des messages
        return rentals.stream().map(rental -> {
            // Récupérer les messages associés à ce rental
            List<Message> messages = messageRepository.findByRentalId(rental.getId());

            // Convertir les messages en MessageDto
            List<MessageDto> messageDtos = toMessageDTOList(messages);

            // Retourner un DTO de Rental avec la liste des MessageDto et les nouveaux champs
            return new AllInfoRentalDto(
                    rental.getId(),
                    rental.getName(),
                    rental.getSurface(),   // Ajout de la surface
                    rental.getPrice(),     // Ajout du prix
                    rental.getPicture(),     // Ajout de l'image
                    messageDtos           // Liste des messages
            );
        }).collect(Collectors.toList());
    }

    private List<MessageDto> toMessageDTOList(List<Message> messages) {
        return messages.stream()
                .map(this::toMessageDTO)  // Pour chaque Message, on utilise la méthode toMessageDTO pour le convertir
                .collect(Collectors.toList());  // Collecte le résultat dans une liste
    }

    private MessageDto toMessageDTO(Message message) {
        return new MessageDto(
                message.getId(),
                message.getRental() != null ? message.getRental().getId() : null,  // Id du Rental associé
                message.getUser() != null ? message.getUser().getId() : null,  // Id de l'Utilisateur associé
                message.getMessage(),  // Contenu du message
                message.getCreatedAt(),  // Date de création du message
                message.getUpdatedAt()   // Date de mise à jour du message
        );
    }
}
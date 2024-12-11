package com.openclassrooms.P3_Full_Stack_portail_locataire.service;

import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.DetailRentalDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.MessageDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.dtos.AllInfoRentalDto;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.Message;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;
import com.openclassrooms.P3_Full_Stack_portail_locataire.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.openclassrooms.P3_Full_Stack_portail_locataire.repository.RentalRepository;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.Rental;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final MessageRepository messageRepository;
private final ImageService imageService;
    public RentalService(RentalRepository rentalRepository, MessageRepository messageRepository, ImageService imageService) {
        this.rentalRepository = rentalRepository;
        this.messageRepository = messageRepository;
        this.imageService = imageService;
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
                    String imageUrl = rental.getPicture() != null ? imageService.getImageUrl(rental.getPicture()) : null;

                    // Créer un DetailRentalDto
                    return new DetailRentalDto(
                            rental.getId(),
                            rental.getName(),
                            rental.getSurface(),
                            rental.getPrice(),
                            imageUrl,
                            rental.getCreatedAt(),
                            rental.getUpdatedAt(),
                            rental.getDescription(),
                            rental.getOwner()

                    );
                });
    }
    public List<AllInfoRentalDto> getAllRentals() {
        // Récupérer toutes les locations
        List<Rental> rentals = rentalRepository.findAll();

        // Pour chaque location, récupérer les messages associés et créer un DTO avec la liste des messages
        return rentals.stream().map(rental -> {
            // Récupérer les messages associés à ce rental
//            List<Message> messages = messageRepository.findByRentalId(rental.getId());
            User owner_id = rental.getOwner();
            // Convertir les messages en MessageDto
//            List<MessageDto> messageDtos = toMessageDTOList(messages);

            // Retourner un DTO de Rental avec la liste des MessageDto et les nouveaux champs
            return new AllInfoRentalDto(
                    rental.getId(),
                    rental.getName(),
                    rental.getSurface(),   // Ajout de la surface
                    rental.getPrice(),     // Ajout du prix
                    rental.getPicture(),
                    rental.getDescription(), // Ajout de l'image
                    owner_id,
                    rental.getCreatedAt(),
                    rental.getUpdatedAt()
            );
        }).collect(Collectors.toList());
    }
}
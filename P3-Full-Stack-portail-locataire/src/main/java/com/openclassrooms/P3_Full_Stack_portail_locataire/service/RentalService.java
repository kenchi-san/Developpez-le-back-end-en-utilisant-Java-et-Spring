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

    public List<AllInfoRentalDto> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();

        // Pour chaque location, récupérer les messages associés et créer un DTO avec la liste des messages
        return rentals.stream().map(rental -> {
            // Récupérer les messages associés à ce rental
            User owner_id = rental.getOwner();


            // Retourner un DTO de Rental avec la liste des MessageDto et les nouveaux champs
            return new AllInfoRentalDto(
                    rental.getId(),
                    rental.getName(),
                    rental.getSurface(),
                    rental.getPrice(),
                    rental.getPicture(),
                    rental.getDescription(),
                    owner_id,
                    rental.getCreatedAt(),
                    rental.getUpdatedAt()
            );
        }).collect(Collectors.toList());
    }
}
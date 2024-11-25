package com.openclassrooms.P3_Full_Stack_portail_locataire.services;

import org.springframework.stereotype.Service;
import com.openclassrooms.P3_Full_Stack_portail_locataire.repository.RentalRepository;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.Rental;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    // Injection via le constructeur
    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    // Récupérer toutes les locations
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    // Enregistrer une nouvelle location
    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    // Trouver une location par ID
    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }
}
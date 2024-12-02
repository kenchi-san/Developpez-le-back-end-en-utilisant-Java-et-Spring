package com.openclassrooms.P3_Full_Stack_portail_locataire.repository;


import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // Méthode pour récupérer les messages par rentalId
    List<Message> findByRentalId(Long rentalId);
}
package com.openclassrooms.P3_Full_Stack_portail_locataire.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CreateMessageDto {
    @NotBlank(message = "Le contenu du message est obligatoire.")
    private String message;

    @NotNull(message = "L'ID de l'utilisateur est obligatoire.")
    private Long user_id;

    @NotNull(message = "L'ID de la location est obligatoire.")
    private Long rental_id;


    public CreateMessageDto(Long user_id, Long rental_id) {
        this.rental_id = rental_id;
        this.user_id = user_id;
    }

    // Getters et Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long userId) {
        this.user_id = userId;
    }

    public Long getRentalId() {
        return rental_id;
    }

    public void setRentalId(Long rentalId) {
        this.rental_id = rental_id;
    }

}

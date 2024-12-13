package com.openclassrooms.P3_Full_Stack_portail_locataire.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateMessageDto {
    @NotBlank(message = "Le contenu du message est obligatoire.")
    private String message;

    @NotNull(message = "L'ID de l'utilisateur est obligatoire.")
    private Long userId;

    @NotNull(message = "L'ID de la location est obligatoire.")
    private Long rentalId;

    // Getters et Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }
    @Override
    public String toString() {
        return "MessageDto{" +
                "message=" + message +
                ", rentalId=" + rentalId +
                ", userId=" + userId +
                '}';
    }
}

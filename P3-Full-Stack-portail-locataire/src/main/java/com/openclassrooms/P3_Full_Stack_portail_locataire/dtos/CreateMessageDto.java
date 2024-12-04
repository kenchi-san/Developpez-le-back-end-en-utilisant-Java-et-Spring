package com.openclassrooms.P3_Full_Stack_portail_locataire.dtos;

import jakarta.validation.constraints.NotBlank;

public class CreateMessageDto {
    @NotBlank(message = "Le contenu du message est obligatoire.")
    private String message;

    // Getters et Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

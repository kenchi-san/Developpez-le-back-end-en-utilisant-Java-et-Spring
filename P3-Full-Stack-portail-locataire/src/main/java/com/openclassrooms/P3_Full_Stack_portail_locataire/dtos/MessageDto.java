package com.openclassrooms.P3_Full_Stack_portail_locataire.dtos;

import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.Message;

import java.time.LocalDateTime;

public class MessageDto {

    private Long id;
    private Long rentalId;  // ID du Rental associé
    private Long userId;    // ID de l'utilisateur associé
    private String message; // Le contenu du message
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructeur
    public MessageDto(Long id, Long rentalId, Long userId, String message, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.rentalId = rentalId;
        this.userId = userId;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

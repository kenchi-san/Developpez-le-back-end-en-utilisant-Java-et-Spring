package com.openclassrooms.P3_Full_Stack_portail_locataire.dtos;

import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.Message;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.Rental;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class MessageDto {

    private Long id;
    private Long rentalId;  // ID du Rental associé
    private Long userId;    // ID de l'utilisateur associé
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // Constructeur
    public MessageDto(Long id, Long rentalId, Long userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.rentalId = rentalId;
        this.userId = userId;
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
    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", rentalId=" + rentalId +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

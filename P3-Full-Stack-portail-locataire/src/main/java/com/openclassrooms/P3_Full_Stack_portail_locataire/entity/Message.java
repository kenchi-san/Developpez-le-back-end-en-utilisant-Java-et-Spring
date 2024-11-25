package com.openclassrooms.P3_Full_Stack_portail_locataire.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "MESSAGES")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    @JsonIgnore
    private Rental rental;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(length = 2000)
    private String message;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getter et Setter pour rental
    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    // Getter et Setter pour user
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getter et Setter pour message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter et Setter pour createdAt
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Getter et Setter pour updatedAt
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
package com.openclassrooms.P3_Full_Stack_portail_locataire.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "DTO contenant toutes les informations principales d'une location.")
public class AllInfoRentalDto {

    @Schema(description = "Identifiant unique de la location.", example = "1")
    private Long id;

    @Schema(description = "Nom de la location.", example = "Appartement T3")
    private String name;

    @Schema(description = "Surface de la location en mètres carrés.", example = "75.5")
    private BigDecimal surface;

    @Schema(description = "Prix de la location en euros.", example = "1200.50")
    private BigDecimal price;

    @Schema(description = "image associée à la location.", example = "https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg")
    private String picture;

    @Schema(description = "voici un superbe apprement")
    private String description;
    @JsonProperty("owner_id")
    private User owner;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    public AllInfoRentalDto(Long id, String name, BigDecimal surface, BigDecimal price, String picture, String description,User owner, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.owner = owner;
        this.description = description;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSurface() {
        return surface;
    }

    public void setSurface(BigDecimal surface) {
        this.surface = surface;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("owner_id")
    public Long getOwnerId() {
        return owner != null ? owner.getId() : null;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
        return "AllInfoRentalDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creation='" + createdAt + '\'' +
                ", modifier='" + updatedAt + '\'' +
                ", price=" + price +
                ", propriétaire=" + owner +
                '}';
    }
}
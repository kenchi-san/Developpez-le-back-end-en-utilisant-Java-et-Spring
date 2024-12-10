package com.openclassrooms.P3_Full_Stack_portail_locataire.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public class AddRentalDto {

    @Schema(description = "Identifiant unique de la location", example = "1")
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Schema(description = "Nom de la location", example = " Appartement T3 ")
    private String name;

    @NotNull(message = "La surface est obligatoire")
    @Positive(message = "La surface doit être un nombre positif")
    @Schema(description = "Surface de la location en mètres carrés", example = "75.5")
    private BigDecimal surface;

    @NotNull(message = "Le prix est obligatoire")
    @Positive(message = "Le prix doit être un nombre positif")
    @Schema(description = "Prix mensuel de la location", example = "1200.50")
    private BigDecimal price;

    @Schema(description = "Description de la location", example = "Bel appartement en centre-ville avec balcon et parking")
    private String description;

    @Schema(description = "Upload l'image de la location", example = "rental.jpg")
    private MultipartFile picture;

    @Schema(description = "Propriétaire de la location, identifié par son email")
    @JsonProperty("owner_id")
    private User owner;

    public AddRentalDto(Long id, String name, BigDecimal surface, BigDecimal price, String description, MultipartFile  picture, User owner) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.description = description;
        this.picture = picture;
        this.owner = owner;

    }

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

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


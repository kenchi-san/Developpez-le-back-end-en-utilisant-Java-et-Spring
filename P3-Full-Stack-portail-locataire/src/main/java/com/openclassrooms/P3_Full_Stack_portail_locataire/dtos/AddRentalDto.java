package com.openclassrooms.P3_Full_Stack_portail_locataire.dtos;

import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public class AddRentalDto {

    @Schema(description = "Donner la description de la location", example = "Appartement T3 avec vue sur la mer")
    private final String Description;
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

    @Schema(description = "URL de l'image de la location", example = "https://example.com/images/rental.jpg")
    private String picture;

    @Schema(description = "Propriétaire de la location, identifié par son email")
    private User owner;
//    private List<MessageDto> messages;

    public AddRentalDto(Long id, String name, BigDecimal surface, BigDecimal price, String Description, String picture, User owner) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.Description = Description;
        this.picture = picture;
        this.owner = owner;
//        this.messages = messages;

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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return Description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}


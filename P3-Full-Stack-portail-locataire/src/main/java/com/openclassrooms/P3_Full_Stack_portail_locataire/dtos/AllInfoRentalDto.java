package com.openclassrooms.P3_Full_Stack_portail_locataire.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
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

    @Schema(description = "Liste des messages associés à la location.")
    private List<MessageDto> messages;

    public AllInfoRentalDto(Long id, String name, BigDecimal surface, BigDecimal price, String picture, List<MessageDto> messages) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.messages = messages;
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

    public List<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDto> messages) {
        this.messages = messages;
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
}
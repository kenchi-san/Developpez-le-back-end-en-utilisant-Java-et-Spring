package com.openclassrooms.P3_Full_Stack_portail_locataire.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

    @Schema(description = "DTO utilisé pour modifier les informations d'une location.")
    public class EditRentalDto {

        @Schema(description = "Identifiant unique de la location.", example = "1")
        private Long id;

        @Schema(description = "Nom de la location.", example = "Appartement T3 rénové")
        private String name;

        @Schema(description = "Surface de la location en mètres carrés.", example = "85.5")
        private BigDecimal surface;

        @Schema(description = "Prix de la location en euros.", example = "1300.00")
        private BigDecimal price;

        @Schema(description = "Description détaillée de la location.", example = "Appartement rénové avec balcon et cuisine moderne")
        private String Description;

        @Schema(description = "Uploader l'image associée à la location.", example = "rental-updated.jpg")
        private MultipartFile picture;

        @Schema(description = "Liste des messages associés (facultatif).")
        private List<MessageDto> messages;
    public EditRentalDto(Long id, String name, BigDecimal surface, BigDecimal price,String Description, MultipartFile picture,List<MessageDto> messages) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.Description = Description;
        this.picture = picture;
        this.messages = messages;

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
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}

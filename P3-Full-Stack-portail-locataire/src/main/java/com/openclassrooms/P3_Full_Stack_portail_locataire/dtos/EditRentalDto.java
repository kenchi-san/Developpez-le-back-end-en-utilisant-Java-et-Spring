package com.openclassrooms.P3_Full_Stack_portail_locataire.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Schema(description = "DTO utilisé pour modifier les informations d'une location.")
public class EditRentalDto {

    @Schema(description = "Nom de la location.", example = "Appartement T3 rénové")
    private String name;

    @Schema(description = "Surface de la location en mètres carrés.", example = "85.5")
    private BigDecimal surface;

    @Schema(description = "Prix de la location en euros.", example = "1300.00")
    private BigDecimal price;

    @Schema(description = "Description détaillée de la location.", example = "Appartement rénové avec balcon et cuisine moderne")
    private String Description;

    public EditRentalDto(String name, BigDecimal surface, BigDecimal price, String Description, MultipartFile picture, User owner) {
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.Description = Description;
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



    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


}

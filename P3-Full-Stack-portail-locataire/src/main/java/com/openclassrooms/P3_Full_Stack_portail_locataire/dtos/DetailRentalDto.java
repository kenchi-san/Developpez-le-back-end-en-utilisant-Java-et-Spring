package com.openclassrooms.P3_Full_Stack_portail_locataire.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class DetailRentalDto {
    private Long id;
    private String name;
    private BigDecimal surface;  // surface
    private BigDecimal price;    // prix
    private String picture; // image
    private String description;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    private User owner;
    public DetailRentalDto(Long id, String name, BigDecimal surface, BigDecimal price, String picture,LocalDateTime createdAt,LocalDateTime updatedAt,String description,User owner) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.description = description;
        this.owner = owner;
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

    @Override
    public String toString() {
        return "DetailRentalDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creation='" + createdAt + '\'' +
                ", modifier='" + updatedAt + '\'' +
                ", price=" + price +
                ", propriétaire=" + owner.getId() +
                '}';
    }

}

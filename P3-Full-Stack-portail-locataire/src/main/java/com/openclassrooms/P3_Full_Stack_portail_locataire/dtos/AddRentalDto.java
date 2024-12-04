package com.openclassrooms.P3_Full_Stack_portail_locataire.dtos;

import com.openclassrooms.P3_Full_Stack_portail_locataire.entity.User;

import java.math.BigDecimal;
import java.util.List;

public class AddRentalDto {
    private Long id;
    private String name;
    private BigDecimal surface;  // surface
    private BigDecimal price;    // prix
    private String Description;
    private String picture; // image
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

    public void setDescription(String description) {
        Description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}


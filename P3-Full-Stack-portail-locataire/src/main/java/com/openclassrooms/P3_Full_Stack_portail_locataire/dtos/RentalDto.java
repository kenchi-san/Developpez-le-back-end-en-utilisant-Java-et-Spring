package com.openclassrooms.P3_Full_Stack_portail_locataire.dtos;

import java.math.BigDecimal;
import java.util.List;

public class RentalDto {

    private Long id;
    private String name;
    private BigDecimal surface;  // surface
    private BigDecimal price;    // prix
    private String picture;    // image
    private List<MessageDto> messages;
    // Constructeur
    public RentalDto(Long id, String name, BigDecimal surface, BigDecimal price, String picture, List<MessageDto> messages) {
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
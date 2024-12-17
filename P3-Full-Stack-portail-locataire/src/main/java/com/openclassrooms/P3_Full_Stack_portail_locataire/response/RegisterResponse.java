package com.openclassrooms.P3_Full_Stack_portail_locataire.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class RegisterResponse {
    private Long userId;
    private String email;
    private String name;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public RegisterResponse(Long userId, String name,String email,LocalDateTime createdAt) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    @Override
    public String toString() {
        return "RegisterResponse{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

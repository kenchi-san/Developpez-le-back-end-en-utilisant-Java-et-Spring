package com.openclassrooms.P3_Full_Stack_portail_locataire.response;

public class RegisterResponse {
    private Long userId;
    private String email;
    private String name;
    private String message;

    // Constructeur par défaut
    public RegisterResponse() {
    }

    // Constructeur avec arguments
    public RegisterResponse(Long userId, String name,String email, String message) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.message = message;
    }

    // Getters et setters
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

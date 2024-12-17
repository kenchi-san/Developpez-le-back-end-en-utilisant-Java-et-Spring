package com.openclassrooms.P3_Full_Stack_portail_locataire.response;

public class LoginResponse {
    private String token;
    private long expiresIn;
    private String message;

    public LoginResponse() {
    }

    public LoginResponse(String token, long expiresIn, String message) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                ", message='" + message + '\'' +
                '}';
    }
}

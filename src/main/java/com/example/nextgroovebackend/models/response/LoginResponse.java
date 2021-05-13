package com.example.nextgroovebackend.models.response;

public class LoginResponse {
    private String JWT;

    public LoginResponse(String JWT) {
        this.JWT = JWT;
    }

    public String getJWT() {
        return JWT;
    }

    public void setJWT(String JWT) {
        this.JWT = JWT;
    }
}

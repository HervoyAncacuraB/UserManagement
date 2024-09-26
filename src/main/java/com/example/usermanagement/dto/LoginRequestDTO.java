package com.example.usermanagement.dto;


import jakarta.validation.constraints.NotEmpty;


public class LoginRequestDTO {

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @NotEmpty(message="Enter an email")
    private String email;

    @NotEmpty(message = "Enter a password")
    private String password;

    public @NotEmpty(message = "Enter an email") String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "Enter an email") String email) {
        this.email = email;
    }

    public @NotEmpty(message = "Enter a password") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "Enter a password") String password) {
        this.password = password;
    }
}

package com.example.usermanagement.dto;


import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
public class LoginRequestDTO {

    @NotNull(message="Enter an email")
    @NotEmpty(message="Enter an email")
    private String email;

    @NotNull(message = "Enter a password")
    @NotEmpty(message = "Enter a password")
    private String password;

}

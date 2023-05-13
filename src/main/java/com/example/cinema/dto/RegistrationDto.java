package com.example.cinema.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
public class RegistrationDto {
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}

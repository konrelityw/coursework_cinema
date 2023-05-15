package com.example.cinema.dto;


import com.example.cinema.models.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class SessionDto {
    private Long id;
    @NotEmpty(message = "Session title should not be empty!")
    private String title;
    @NotEmpty(message = "Amount of places should not be empty!")
    private String amountOfPlaces;
    private LocalDateTime time;
    @NotEmpty(message = "Price should not be empty!")
    private String price;
    private UserEntity createdBy;
    private List<PlaceDto> places;
}

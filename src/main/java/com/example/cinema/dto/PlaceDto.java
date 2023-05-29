package com.example.cinema.dto;

import com.example.cinema.models.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private Long id;
    private String row;
    private String number;
    private Session session;
    private boolean isBooked;
}

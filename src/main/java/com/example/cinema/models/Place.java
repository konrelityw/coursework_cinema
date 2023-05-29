package com.example.cinema.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String row;
    private String number;
    private boolean isBooked;
    @ManyToOne
    @JoinColumn(name = "cinema_session_id", nullable = false)
    private Session session;

}

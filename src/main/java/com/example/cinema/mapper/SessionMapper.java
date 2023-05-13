package com.example.cinema.mapper;

import com.example.cinema.dto.SessionDto;
import com.example.cinema.models.Session;

import java.util.stream.Collectors;

import static com.example.cinema.mapper.PlaceMapper.mapToPlaceDto;

public class SessionMapper {
    public static SessionDto mapToSessionDto(Session session) {
        SessionDto sessionDto = SessionDto.builder()
                .id(session.getId())
                .title(session.getTitle())
                .amountOfPlaces(session.getAmountOfPlaces())
                .price(session.getPrice())
                .time(session.getTime())
                .places(session.getPlaces().stream().map(place -> mapToPlaceDto(place)).collect(Collectors.toList()))
                .build();
        return sessionDto;
    }
    public static Session mapToSession(SessionDto session) {
        Session sessionDto = Session.builder()
                .id(session.getId())
                .title(session.getTitle())
                .amountOfPlaces(session.getAmountOfPlaces())
                .price(session.getPrice())
                .time(session.getTime())
                .build();
        return sessionDto;
    }
}

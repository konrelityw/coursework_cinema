package com.example.cinema.service;

import com.example.cinema.dto.PlaceDto;

import java.util.List;

public interface PlaceService {
    void createPlace(Long sessionId, PlaceDto placeDto);

    List<PlaceDto> findAllPlaces();

    PlaceDto findByPlaceId(Long placeId);

    void updatePlace(PlaceDto placeDto);

    void deletePlace(long placeId);
}

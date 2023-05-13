package com.example.cinema.mapper;

import com.example.cinema.dto.PlaceDto;
import com.example.cinema.models.Place;

public class PlaceMapper {
    public static Place mapToPlace(PlaceDto placeDto){
        return Place.builder()
                .id(placeDto.getId())
                .row(placeDto.getRow())
                .number(placeDto.getNumber())
                .session(placeDto.getSession())
                .build();
    }
    public static PlaceDto mapToPlaceDto(Place place){
        return PlaceDto.builder()
                .id(place.getId())
                .row(place.getRow())
                .number(place.getNumber())
                .session(place.getSession())
                .build();
    }
}

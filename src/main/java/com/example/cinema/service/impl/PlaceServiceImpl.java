package com.example.cinema.service.impl;

import com.example.cinema.dto.PlaceDto;
import com.example.cinema.dto.SessionDto;
import com.example.cinema.mapper.PlaceMapper;
import com.example.cinema.models.Place;
import com.example.cinema.models.Session;
import com.example.cinema.repository.PlaceRepository;
import com.example.cinema.repository.SessionRepository;
import com.example.cinema.service.PlaceService;
import com.example.cinema.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.cinema.mapper.PlaceMapper.mapToPlace;
import static com.example.cinema.mapper.PlaceMapper.mapToPlaceDto;
import static com.example.cinema.mapper.SessionMapper.mapToSession;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final SessionService sessionService;
    private final PlaceRepository placeRepository;

    @Override
    public void createPlace(Long sessionId, PlaceDto placeDto) {
        SessionDto session = sessionService.findSessionById(sessionId);
        Place place = mapToPlace(placeDto);
        place.setSession(mapToSession(session));
        placeRepository.save(place);
    }

    @Override
    public List<PlaceDto> findAllPlaces() {
        List<Place> places = placeRepository.findAll();
        return places.stream().map(PlaceMapper::mapToPlaceDto).collect(Collectors.toList());
    }

    @Override
    public PlaceDto findByPlaceId(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(()->new IllegalArgumentException("Place with Id %s not found".formatted(placeId)));
        return mapToPlaceDto(place);
    }

    @Override
    public void updatePlace(PlaceDto placeDto, String username) {
        Place place = mapToPlace(placeDto);
        placeRepository.save(place);
    }

    @Override
    public void deletePlace(long placeId) {
        placeRepository.deleteById(placeId);
    }

    @Override
    public void reservePlace(Long placeId) {
        if (placeRepository.existsById(placeId)) {
            Place place = placeRepository.findById(placeId).orElseThrow(() -> new RuntimeException("Place not found"));
            place.setBooked(true);
            placeRepository.save(place);
        } else {
            throw new RuntimeException("Place not found");
        }
    }

}


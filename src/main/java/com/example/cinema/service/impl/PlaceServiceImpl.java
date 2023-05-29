package com.example.cinema.service.impl;

import com.example.cinema.dto.PlaceDto;
import com.example.cinema.models.Place;
import com.example.cinema.models.Session;
import com.example.cinema.repository.PlaceRepository;
import com.example.cinema.repository.SessionRepository;
import com.example.cinema.service.PlaceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.cinema.mapper.PlaceMapper.mapToPlace;
import static com.example.cinema.mapper.PlaceMapper.mapToPlaceDto;
import static com.example.cinema.mapper.SessionMapper.mapToSession;

@Service
public class PlaceServiceImpl implements PlaceService {
    private PlaceRepository placeRepository;
    private SessionRepository sessionRepository;
    public PlaceServiceImpl(PlaceRepository placeRepository, SessionRepository sessionRepository) {
        this.placeRepository = placeRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void createPlace(Long sessionId, PlaceDto placeDto) {
        Session session = sessionRepository.findById(sessionId).get();
        Place place = mapToPlace(placeDto);
        place.setSession(session);
        placeRepository.save(place);
    }

    @Override
    public List<PlaceDto> findAllPlaces() {
        List<Place> places = placeRepository.findAll();
        return places.stream().map(place -> mapToPlaceDto(place)).collect(Collectors.toList());
    }

    @Override
    public PlaceDto findByPlaceId(Long placeId) {
        Place place = placeRepository.findById(placeId).get();
        return mapToPlaceDto(place);
    }

    @Override
    public void updatePlace(PlaceDto placeDto) {
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


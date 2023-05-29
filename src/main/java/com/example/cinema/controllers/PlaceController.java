package com.example.cinema.controllers;

import com.example.cinema.dto.PlaceDto;
import com.example.cinema.dto.SessionDto;
import com.example.cinema.models.Place;
import com.example.cinema.models.UserEntity;
import com.example.cinema.security.SecurityUtil;
import com.example.cinema.service.PlaceService;
import com.example.cinema.service.SessionService;
import com.example.cinema.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PlaceController {
    private PlaceService placeService;
    private UserService userService;
    private SessionService sessionService;

    @Autowired
    public PlaceController(PlaceService placeService, UserService userService, SessionService sessionService) {
        this.placeService = placeService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/places")
    public String placeList(Model model, SessionDto sessionDto) {
        UserEntity user = new UserEntity();
        List<PlaceDto> places = placeService.findAllPlaces();
        List<PlaceDto> availablePlaces = new ArrayList<>();
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("seance", sessionDto);
        for (PlaceDto place : places) {
            if (!place.isBooked()) {
                availablePlaces.add(place);
            }
        }

        model.addAttribute("places", availablePlaces);
        return "places-list";
    }

    @GetMapping("/places/{placeId}")
    public String viewPlace(@PathVariable("placeId")Long placeId, Model model){
        UserEntity user = new UserEntity();
        PlaceDto placeDto = placeService.findByPlaceId(placeId);
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("filmSession", placeDto.getSession());
        model.addAttribute("user", user);
        model.addAttribute("place", placeDto);
        return "places-detail";
    }

    @GetMapping("places/{sessionId}/new")
    public String createNewPlace(@PathVariable("sessionId") Long sessionId, Model model) {
        Place place = new Place();
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("place", place);
        return "places-create";
    }
    @GetMapping("/places/{placeId}/edit")
    public String editPlace(@PathVariable("placeId") Long placeId, Model model) {
        PlaceDto place = placeService.findByPlaceId(placeId);
        model.addAttribute("place", place);
        return "places-edit";
    }

    @PostMapping("/places/{sessionId}")
    public String createPlace(@PathVariable("sessionId") Long sessionId, @ModelAttribute("place") PlaceDto placeDto,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("place", placeDto);
            return "sessions-detail";
        }
        placeService.createPlace(sessionId, placeDto);
        return "redirect:/sessions/" + sessionId;
    }

    @PostMapping("/places/{placeId}/edit")
    public String updatePlace(@PathVariable("placeId") Long placeId,
                                @Valid @ModelAttribute("place") PlaceDto place,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("place", place);
            return "places-edit";
        }
        PlaceDto placeDto = placeService.findByPlaceId(placeId);
        place.setId(placeId);
        place.setSession(placeDto.getSession());
        placeService.updatePlace(place);
        return "redirect:/{sessionId}/places";
    }
    @GetMapping("/places/{placeId}/delete")
    public String deletePlace(@PathVariable("placeId") long placeId){
        placeService.deletePlace(placeId);
        return "redirect:/places";
    }
    @PostMapping("/places/{placeId}/reserve")
    public String reservePlace(@PathVariable("placeId") Long placeId) {
        placeService.reservePlace(placeId);
        return "redirect:/places";
    }

}

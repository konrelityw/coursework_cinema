package com.example.cinema.controllers;

import com.example.cinema.models.Session;
import com.example.cinema.models.UserEntity;
import com.example.cinema.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import com.example.cinema.dto.SessionDto;
import com.example.cinema.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class SessionController {
    private SessionService sessionService;
    private UserService userService;

    @Autowired
    public SessionController(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @GetMapping("/sessions")
    public String listSessions(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        model.addAttribute("sessions", sessionService.findAllSessions());
        return "sessions-list";
    }

    @GetMapping("/sessions/{sessionId}")
    public String sessionDetail(@PathVariable("sessionId") long sessionId, Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        model.addAttribute("seance", sessionService.findSessionById(sessionId));
        return "sessions-detail";
    }

    @GetMapping("/sessions/new")
    public String createNewSession(Model model) {
        Session seance = new Session();
        model.addAttribute("seance", seance);
        return "sessions-create";
    }

    @GetMapping("/sessions/{sessionId}/delete")
    public String deleteSession(@PathVariable("sessionId") long sessionId) {
        sessionService.delete(sessionId);
        return "redirect:/sessions";
    }

    @PostMapping("/sessions/new")
    public String saveSession(@Valid @ModelAttribute("session") SessionDto sessionDto,
                              BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("seance", sessionDto);
            return "sessions-create";
        }
        sessionService.saveSession(sessionDto, principal.getName());
        return "redirect:/sessions";
    }

    @GetMapping("/sessions/{sessionId}/edit")
    public String editSession(@PathVariable("sessionId") Long sessionId, Model model) {
        SessionDto seance = sessionService.findSessionById(sessionId);
        model.addAttribute("seance", seance);
        return "sessions-edit";
    }

    @PostMapping("/sessions/{sessionId}/edit")
    public String updateSession(@PathVariable("sessionId") Long sessionId,
                                @Valid @ModelAttribute("session") SessionDto seance,
                                BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("seance", seance);
            return "sessions-edit";
        }
        seance.setId(sessionId);
        sessionService.updateSession(seance, principal.getName());
        return "redirect:/sessions";
    }
}

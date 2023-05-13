package com.example.cinema.controllers;

import com.example.cinema.models.Session;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import com.example.cinema.dto.SessionDto;
import com.example.cinema.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SessionController {
    private SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/sessions")
    public String listSessions(Model model) {
        List<SessionDto> sessions = sessionService.findAllSessions();
        model.addAttribute("sessions", sessions);
        return "sessions-list";
    }

    @GetMapping("/sessions/{sessionId}")
    public String sessionDetail(@PathVariable("{sessionId}")long sessionId, Model model){
        SessionDto sessionDto = sessionService.findSessionById(sessionId);
        model.addAttribute("session", sessionDto);
        return "sessions-detail";
    }

    @GetMapping("/sessions/new")
    public String createNewSession(Model model) {
        Session session = new Session();
        model.addAttribute("session", session);
        return "sessions-create";
    }
    @GetMapping("/sessions/{sessionId}/delete")
    public String deleteSession(@PathVariable("{sessionId}")long sessionId){
        sessionService.delete(sessionId);
        return "redirect:/sessions";
    }
    @GetMapping("sessions/search")
    public String searchSession(@RequestParam(value = "query")String query, Model model){
        List<SessionDto> sessions = sessionService.searchSessions(query);
        model.addAttribute("sessions", sessions);
        return "sessions-list";
    }
    @PostMapping("/sessions/new")
    public String saveSession(@Valid @ModelAttribute("session") SessionDto sessionDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("session", sessionDto);
            return "sessions-create";
        }
        sessionService.saveSession(sessionDto);
        return "redirect:/sessions";
    }

    @GetMapping("/sessions/{sessionId}/edit")
    public String editSession(@PathVariable("sessionId") Long sessionId, Model model) {
        SessionDto session = sessionService.findSessionById(sessionId);
        model.addAttribute("session", session);
        return "sessions-edit";
    }

    @PostMapping("/sessions/{sessionId}/edit")
    public String updateSession(@PathVariable("sessionId") Long sessionId,
                                @Valid @ModelAttribute("session") SessionDto session,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("session", session);
            return "sessions-edit";
        }
        session.setId(sessionId);
        sessionService.updateSession(session);
        return "redirect:/sessions";
    }
}

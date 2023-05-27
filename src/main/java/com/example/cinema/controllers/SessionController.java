package com.example.cinema.controllers;

import com.example.cinema.models.Session;
import com.example.cinema.models.UserEntity;
import com.example.cinema.security.SecurityUtil;
import com.example.cinema.service.UserService;
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
    private UserService userService;

    @Autowired
    public SessionController(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @GetMapping("/sessions")
    public String listSessions(Model model) {
        UserEntity user = new UserEntity();
        List<SessionDto> seance = sessionService.findAllSessions();
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("sessions", seance);
        return "sessions-list";
    }

    @GetMapping("/sessions/{sessionId}")
    public String sessionDetail(@PathVariable("sessionId") long sessionId, Model model) {
        UserEntity user = new UserEntity();
        SessionDto sessionDto = sessionService.findSessionById(sessionId);
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("seance", sessionDto);
        return "sessions-detail";
    }

    @GetMapping("/sessions/new")
    public String createNewSession(Model model) {
        Session seance = new Session();
        model.addAttribute("seance", seance);
        return "sessions-create";
    }
    @GetMapping("/sessions/{sessionId}/delete")
    public String deleteSession(@PathVariable("sessionId")long sessionId){
        sessionService.delete(sessionId);
        return "redirect:/sessions";
    }
    @GetMapping("sessions/search")
    public String searchSession(@RequestParam(value = "seance")String seance, Model model){
        List<SessionDto> session = sessionService.searchSessions(seance);
        model.addAttribute("seance", session);
        return "sessions-list";
    }
    @PostMapping("/sessions/new")
    public String saveSession(@Valid @ModelAttribute("session") SessionDto sessionDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("seance", sessionDto);
            return "sessions-create";
        }
        sessionService.saveSession(sessionDto);
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
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("seance", seance);
            return "sessions-edit";
        }
        seance.setId(sessionId);
        sessionService.updateSession(seance);
        return "redirect:/sessions";
    }
}

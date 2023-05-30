package com.example.cinema.service;

import com.example.cinema.dto.SessionDto;
import com.example.cinema.models.Session;

import java.util.List;

public interface SessionService {
    List<SessionDto> findAllSessions();

    Session saveSession(SessionDto sessionDto, String username);

    SessionDto findSessionById(long sessionId);

    void updateSession(SessionDto session, String username);

    void delete(long sessionId);

}

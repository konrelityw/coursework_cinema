package com.example.cinema.service;

import com.example.cinema.dto.SessionDto;
import com.example.cinema.models.Session;

import java.util.List;

public interface SessionService {
    List<SessionDto> findAllSessions();

    Session saveSession(SessionDto sessionDto);

    SessionDto findSessionById(long sessionId);

    void updateSession(SessionDto session);

    void delete(long sessionId);
    List<SessionDto> searchSessions(String query);
}

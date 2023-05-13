package com.example.cinema.service.impl;

import com.example.cinema.dto.SessionDto;
import com.example.cinema.models.Session;
import com.example.cinema.repository.SessionRepository;
import com.example.cinema.service.SessionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.cinema.mapper.SessionMapper.mapToSession;
import static com.example.cinema.mapper.SessionMapper.mapToSessionDto;

@Service
public class SessionServiceImpl implements SessionService {
    private SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public List<SessionDto> findAllSessions() {
        List<Session> sessions = sessionRepository.findAll();
        return sessions.stream().map((session) -> mapToSessionDto(session)).collect(Collectors.toList());
    }

    @Override
    public Session saveSession(SessionDto sessionDto) {
        Session session = mapToSession(sessionDto);
        return sessionRepository.save(session);
    }

    @Override
    public SessionDto findSessionById(long sessionId) {
        Session session = sessionRepository.findById(sessionId).get();
        return mapToSessionDto(session);
    }

    @Override
    public void updateSession(SessionDto sessionDto) {
        Session session = mapToSession(sessionDto);
        sessionRepository.save(session);
    }

    @Override
    public void delete(long sessionId) {
        sessionRepository.deleteById(sessionId);
    }

    @Override
    public List<SessionDto> searchSessions(String query) {
        List<Session> sessions = sessionRepository.searchSessions(query);
        return sessions.stream().map(session -> mapToSessionDto(session)).collect(Collectors.toList());
    }
}

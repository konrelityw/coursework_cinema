package com.example.cinema.service.impl;

import com.example.cinema.dto.SessionDto;
import com.example.cinema.mapper.SessionMapper;
import com.example.cinema.models.Session;
import com.example.cinema.models.UserEntity;
import com.example.cinema.repository.SessionRepository;
import com.example.cinema.repository.UserRepository;
import com.example.cinema.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.cinema.mapper.SessionMapper.mapToSession;
import static com.example.cinema.mapper.SessionMapper.mapToSessionDto;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

    @Override
    public List<SessionDto> findAllSessions() {
        List<Session> sessions = sessionRepository.findAll();
        return sessions.stream().map(SessionMapper::mapToSessionDto).collect(Collectors.toList());
    }

    @Override
    public Session saveSession(SessionDto sessionDto,String username) {
        UserEntity user = userRepository.findByUsername(username);
        Session session = mapToSession(sessionDto);
        session.setCreatedBy(user);
        return sessionRepository.save(session);
    }

    @Override
    public SessionDto findSessionById(long sessionId) {
        Session session = sessionRepository.findById(sessionId).get();
        return mapToSessionDto(session);
    }

    @Override
    public void updateSession(SessionDto sessionDto, String username) {
        UserEntity user = userRepository.findByUsername(username);
        Session session = mapToSession(sessionDto);
        session.setCreatedBy(user);
        sessionRepository.save(session);
    }

    @Override
    public void delete(long sessionId) {
        sessionRepository.deleteById(sessionId);
    }

}

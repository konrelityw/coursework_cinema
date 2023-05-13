package com.example.cinema.repository;

import com.example.cinema.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Session getByTitle(String title);
    @Query("SELECT s from  Session s where s.title LIKE CONCAT('%', :query, '%')")
    List<Session> searchSessions(String query);
}

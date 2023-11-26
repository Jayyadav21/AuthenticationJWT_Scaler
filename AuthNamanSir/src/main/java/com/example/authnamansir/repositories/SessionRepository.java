package com.example.authnamansir.repositories;

import com.example.authnamansir.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {

        Optional<Session> findByTokenAndUserId(String token,Long userID);

}

package com.cryptovision.backend.repository;

import com.cryptovision.backend.entity.Authentication;
import com.cryptovision.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {
    Optional<Authentication> findByUser(User user);
    Optional<Authentication> findByRefreshToken(String refreshToken);
}
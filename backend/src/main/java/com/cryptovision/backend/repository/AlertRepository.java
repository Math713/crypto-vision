package com.cryptovision.backend.repository;

import com.cryptovision.backend.entity.Alert;
import com.cryptovision.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByUserAndActiveTrue(User user);
    List<Alert> findByActiveTrue();
}
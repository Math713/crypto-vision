package com.cryptovision.backend.repository;

import com.cryptovision.backend.entity.Cryptocurrency;
import com.cryptovision.backend.entity.Portfolio;
import com.cryptovision.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUser(User user);
    Optional<Portfolio> findByUserAndCryptocurrency(User user, Cryptocurrency cryptocurrency);
}
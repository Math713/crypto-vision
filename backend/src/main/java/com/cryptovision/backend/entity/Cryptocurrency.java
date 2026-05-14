package com.cryptovision.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "criptomoedas")
public class Cryptocurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "simbolo", nullable = false, unique = true)
    private String symbol;

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "preco_atual", nullable = false)
    private BigDecimal currentPrice;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate // for the @Schedule
    private void onSave() { this.updatedAt = LocalDateTime.now(); }
}
package com.cryptovision.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Table(name = "portfolios",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "cripto_id"}))
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cripto_id", nullable = false)
    private Cryptocurrency cryptocurrency;

    @Column(name = "quantidade", nullable = false)
    private BigDecimal quantity;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    private void onSave() { this.updatedAt = LocalDateTime.now(); }
}
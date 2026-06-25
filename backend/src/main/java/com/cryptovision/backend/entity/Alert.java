package com.cryptovision.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "alertas")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // just to make it explicit
    @JoinColumn(name = "usuario_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cripto_id", nullable = false)
    private Cryptocurrency cryptocurrency;

    @Column(name = "preco_alvo", nullable = false)
    private BigDecimal targetPrice;

    @Column(name = "ativo", nullable = false)
    private Boolean active = false;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() { this.createdAt = LocalDateTime.now(); }
}
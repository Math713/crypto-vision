package com.cryptovision.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "historico_precos")
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // just to make it explicit
    @JoinColumn(name = "cripto_id", nullable = false)
    private Cryptocurrency cripto;

    @Column(name = "preco", nullable = false)
    private BigDecimal price;

    @Column(name = "registrado_em", nullable = false)
    private LocalDateTime registeredAt;

    @PrePersist
    private void prePersist(){ this.registeredAt = LocalDateTime.now(); }
}
package com.cryptovision.backend.entity;

import com.cryptovision.backend.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "transacoes")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cripto_id", nullable = false)
    private Cryptocurrency cryptocurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TransactionType type;

    @Column(name = "quantidade", nullable = false)
    private BigDecimal quantity;

    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "realizado_em", nullable = false)
    private LocalDateTime executedAt;

    @PrePersist
    private void prePersist() { this.executedAt = LocalDateTime.now(); }
}
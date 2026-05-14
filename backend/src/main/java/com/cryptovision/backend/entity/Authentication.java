package com.cryptovision.backend.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Table(name = "autenticacao")
public class Authentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // just to make it explicit
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private User user;

    @Column(name = "token_2fa")
    private String token2fa;

    @Column(name = "ativo_2fa", nullable = false)
    private Boolean active2fa = false;

    @Column(name = "ultimo_login")
    private LocalDateTime lastLogin;

    @Column(name = "refresh_token")
    private String refreshToken;
}
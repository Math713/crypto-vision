package com.cryptovision.backend.entity;

import com.cryptovision.backend.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // just to make it explicit
    @JoinColumn(name = "usuario_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, columnDefinition = "role_tipo")
    private RoleType type;

    @Column(name = "atribuido_em", nullable = false, updatable = false)
    private LocalDateTime assignedAt;

    @PrePersist
    private void prePersist() { this.assignedAt = LocalDateTime.now(); }
}

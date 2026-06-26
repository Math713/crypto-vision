package com.cryptovision.backend.repository;

import com.cryptovision.backend.entity.User;
import com.cryptovision.backend.enums.ProfileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE usuarios SET nome = :name, perfil = CAST(:profileType AS perfil_tipo) WHERE id = :id", nativeQuery = true)
    void updateUser(@Param("id") Long id,
                    @Param("name") String name,
                    @Param("profileType") String profileType);

    @Modifying
    @Query("UPDATE User u SET u.passwordHash = :hash WHERE u.id = :id")
    void updatePassword(@Param("id") Long id,
                        @Param("hash") String hash);
}
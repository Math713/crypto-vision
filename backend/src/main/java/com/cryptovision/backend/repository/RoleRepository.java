package com.cryptovision.backend.repository;

import com.cryptovision.backend.entity.Role;
import com.cryptovision.backend.entity.User;
import com.cryptovision.backend.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByUser(User user);
    boolean existsByUserAndType(User user, RoleType type);
}
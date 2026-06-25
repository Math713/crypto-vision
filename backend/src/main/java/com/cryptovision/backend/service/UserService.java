package com.cryptovision.backend.service;

import com.cryptovision.backend.dto.ChangePasswordRequest;
import com.cryptovision.backend.dto.UpdateUserRequest;
import com.cryptovision.backend.dto.UserResponseDTO;
import com.cryptovision.backend.entity.User;
import com.cryptovision.backend.exception.InvalidPasswordException;
import com.cryptovision.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO getMe(User user) {
        return toDTO(user);
    }

    @Transactional
    public UserResponseDTO updateMe(User user, UpdateUserRequest request) {
        user.setName(request.name());
        user.setProfileType(request.profileType());
        userRepository.save(user);
        return toDTO(user);
    }

    @Transactional
    public void changePassword(User user, ChangePasswordRequest request) {
        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw new InvalidPasswordException("Senha atual incorreta");
        }
        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    private UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getProfileType(),
                user.getCreatedAt()
        );
    }
}
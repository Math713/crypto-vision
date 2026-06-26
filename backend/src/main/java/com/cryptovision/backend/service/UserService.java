package com.cryptovision.backend.service;

import com.cryptovision.backend.dto.ChangePasswordRequest;
import com.cryptovision.backend.dto.UpdateUserRequest;
import com.cryptovision.backend.dto.UserResponseDTO;
import com.cryptovision.backend.entity.User;
import com.cryptovision.backend.exception.InvalidPasswordException;
import com.cryptovision.backend.exception.UserNotFoundException;
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
        return toDto(user);
    }

    @Transactional
    public UserResponseDTO updateMe(User user, UpdateUserRequest request) {
        userRepository.updateUser(user.getId(), request.name(), request.profileType().name());

        User updated = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getEmail()));

        return toDto(updated);
    }

    @Transactional
    public void changePassword(User user, ChangePasswordRequest request) {
        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw new InvalidPasswordException("Senha atual incorreta");
        }

        String newHash = passwordEncoder.encode(request.newPassword());
        userRepository.updatePassword(user.getId(), newHash);
    }

    private UserResponseDTO toDto(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getProfileType(),
                user.getCreatedAt()
        );
    }
}
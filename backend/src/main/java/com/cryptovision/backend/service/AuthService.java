package com.cryptovision.backend.service;

import com.cryptovision.backend.dto.AuthResponse;
import com.cryptovision.backend.dto.LoginRequest;
import com.cryptovision.backend.dto.RefreshRequest;
import com.cryptovision.backend.dto.RegisterRequest;
import com.cryptovision.backend.entity.Authentication;
import com.cryptovision.backend.entity.User;
import com.cryptovision.backend.exception.EmailAlreadyExistsException;
import com.cryptovision.backend.exception.InvalidTokenException;
import com.cryptovision.backend.exception.UserNotFoundException;
import com.cryptovision.backend.repository.AuthenticationRepository;
import com.cryptovision.backend.repository.UserRepository;
import com.cryptovision.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setProfileType(request.profileType());
        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Authentication auth = new Authentication();
        auth.setUser(user);
        auth.setRefreshToken(refreshToken);
        authenticationRepository.save(auth);

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException(request.email()));

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Authentication auth = authenticationRepository.findByUser(user)
                .orElseThrow(() -> new UserNotFoundException(request.email()));
        auth.setRefreshToken(refreshToken);
        auth.setLastLogin(LocalDateTime.now());
        authenticationRepository.save(auth);

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse refresh(RefreshRequest request) {
        Authentication auth = authenticationRepository.findByRefreshToken(request.refreshToken())
                .orElseThrow(InvalidTokenException::new);

        User user = auth.getUser();

        if (!jwtService.isTokenValid(request.refreshToken(), user)) {
            throw new InvalidTokenException();
        }

        String accessToken = jwtService.generateAccessToken(user);
        return new AuthResponse(accessToken, request.refreshToken());
    }

    public void logout(User user) {
        Authentication auth = authenticationRepository.findByUser(user)
                .orElseThrow(() -> new UserNotFoundException(user.getEmail()));

        auth.setRefreshToken(null);
        authenticationRepository.save(auth);
    }
}
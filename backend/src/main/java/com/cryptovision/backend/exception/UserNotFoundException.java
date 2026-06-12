package com.cryptovision.backend.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String email) {
    super("Usuário não encontrado com email: " + email);
  }

  public UserNotFoundException(Long id) {
    super("Usuário não encontrado com id: " + id);
  }
}
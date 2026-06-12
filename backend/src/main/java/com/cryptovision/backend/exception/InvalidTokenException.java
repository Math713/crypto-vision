package com.cryptovision.backend.exception;

public class InvalidTokenException extends RuntimeException {
  public InvalidTokenException() {
    super("Token invalído ou expirado");
  }
}
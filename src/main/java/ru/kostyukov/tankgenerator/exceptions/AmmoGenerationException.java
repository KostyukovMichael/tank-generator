package ru.kostyukov.tankgenerator.exceptions;

public class AmmoGenerationException extends RuntimeException {
  public AmmoGenerationException(String message) {
    super(message);
  }

  public AmmoGenerationException(String message, Throwable cause) {
    super(message, cause);
  }
}

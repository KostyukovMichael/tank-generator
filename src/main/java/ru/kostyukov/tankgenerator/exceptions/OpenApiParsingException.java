package ru.kostyukov.tankgenerator.exceptions;

public class OpenApiParsingException extends RuntimeException {
  public OpenApiParsingException(String message) {
    super(message);
  }

  public OpenApiParsingException(String message, Throwable cause) {
    super(message, cause);
  }
}

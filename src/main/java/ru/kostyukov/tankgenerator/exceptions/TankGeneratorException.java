package ru.kostyukov.tankgenerator.exceptions;

public class TankGeneratorException extends RuntimeException {
  public TankGeneratorException(String message) {
    super(message);
  }

  public TankGeneratorException(String message, Throwable cause) {
    super(message, cause);
  }
}

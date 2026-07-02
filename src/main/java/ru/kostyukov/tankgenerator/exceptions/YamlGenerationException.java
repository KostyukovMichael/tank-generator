package ru.kostyukov.tankgenerator.exceptions;

public class YamlGenerationException extends RuntimeException {
  public YamlGenerationException(String message) {
    super(message);
  }

  public YamlGenerationException(String message, Throwable cause) {
    super(message, cause);
  }
}

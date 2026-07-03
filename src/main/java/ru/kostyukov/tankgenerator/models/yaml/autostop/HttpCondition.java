package ru.kostyukov.tankgenerator.models.yaml.autostop;

public record HttpCondition(String httpCode, String httpLimit, String httpWindow)
    implements Condition {
  @Override
  public String toYamlValue() {
    return "http(%s, %s, %s)".formatted(httpCode, httpLimit, httpWindow);
  }
}

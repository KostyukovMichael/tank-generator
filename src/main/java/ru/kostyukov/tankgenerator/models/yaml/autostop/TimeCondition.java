package ru.kostyukov.tankgenerator.models.yaml.autostop;

public record TimeCondition(String responseTime, String timeWindow) implements Condition {
  @Override
  public String toYamlValue() {
    return "time(%s, %s)".formatted(responseTime, timeWindow);
  }
}

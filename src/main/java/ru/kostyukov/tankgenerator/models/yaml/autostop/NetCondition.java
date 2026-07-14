package ru.kostyukov.tankgenerator.models.yaml.autostop;

public record NetCondition(String netCode, String netLimit, String netWindow) implements Condition {
  @Override
  public String toYamlValue() {
    return "net(%s, %s, %s)".formatted(netCode, netLimit, netWindow);
  }
}

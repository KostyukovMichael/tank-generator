package ru.kostyukov.tankgenerator.models.yaml;

public record LinearSchedule(int rpsStart, int rpsEnd, String duration) implements Schedule {
  @Override
  public String toYamlValue() {
    return "line(%d, %d, %s)".formatted(rpsStart, rpsEnd, duration);
  }
}

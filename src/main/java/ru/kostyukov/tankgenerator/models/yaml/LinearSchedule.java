package ru.kostyukov.tankgenerator.models.yaml;

public record LinearSchedule(int rpsStart, int rpsEnd, String duration) implements Schedule {
  @Override
  public String toYamlValue() {
    return String.format("line(%d, %d, %s)", rpsStart, rpsEnd, duration);
  }
}

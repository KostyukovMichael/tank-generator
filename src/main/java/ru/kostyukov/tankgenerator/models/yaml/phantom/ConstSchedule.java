package ru.kostyukov.tankgenerator.models.yaml.phantom;

public record ConstSchedule(int rps, String duration) implements Schedule {
  @Override
  public String toYamlValue() {
    return "const(%d, %s)".formatted(rps, duration);
  }
}

package ru.kostyukov.tankgenerator.models.yaml;

public record StepSchedule(int rpsStart, int rpsEnd, int rpsStep, String duration)
    implements Schedule {
  @Override
  public String toYamlValue() {
    return "step(%d, %d, %d, %s)".formatted(rpsStart, rpsEnd, rpsStep, duration);
  }
}

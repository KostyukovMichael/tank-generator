package ru.kostyukov.tankgenerator.models.yaml;

import com.fasterxml.jackson.annotation.JsonValue;

public interface Schedule {
  @JsonValue
  String toYamlValue();
}

package ru.kostyukov.tankgenerator.models.yaml.phantom;

import com.fasterxml.jackson.annotation.JsonValue;

public interface Schedule {
  @JsonValue
  String toYamlValue();
}

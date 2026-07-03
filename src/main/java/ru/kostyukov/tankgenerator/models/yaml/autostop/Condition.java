package ru.kostyukov.tankgenerator.models.yaml.autostop;

import com.fasterxml.jackson.annotation.JsonValue;

public interface Condition {
  @JsonValue
  String toYamlValue();
}

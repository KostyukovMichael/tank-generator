package ru.kostyukov.tankgenerator.models.yaml;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoadProfile(
    @JsonProperty("load_type") String loadType, @JsonProperty("schedule") Schedule schedule) {}

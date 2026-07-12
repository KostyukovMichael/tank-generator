package ru.kostyukov.tankgenerator.models.yaml;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InfluxConfig(
    @JsonProperty("enabled") boolean enabled,
    @JsonProperty("address") String address,
    @JsonProperty("port") int port,
    @JsonProperty("database") String database,
    @JsonProperty("tank_tag") String tankTag) {}

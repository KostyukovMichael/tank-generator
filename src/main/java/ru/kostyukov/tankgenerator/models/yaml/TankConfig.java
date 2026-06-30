package ru.kostyukov.tankgenerator.models.yaml;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TankConfig(@JsonProperty("phantom") PhantomConfig phantomConfig) {}

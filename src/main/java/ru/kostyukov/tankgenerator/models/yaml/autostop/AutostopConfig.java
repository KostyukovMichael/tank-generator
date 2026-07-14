package ru.kostyukov.tankgenerator.models.yaml.autostop;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record AutostopConfig(@JsonProperty("autostop") List<Condition> conditions) {}

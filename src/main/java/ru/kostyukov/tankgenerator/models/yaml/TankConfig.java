package ru.kostyukov.tankgenerator.models.yaml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.kostyukov.tankgenerator.models.yaml.autostop.AutostopConfig;
import ru.kostyukov.tankgenerator.models.yaml.phantom.PhantomConfig;

public record TankConfig(
    @JsonProperty("phantom") PhantomConfig phantomConfig,
    @JsonProperty("autostop") @JsonInclude(JsonInclude.Include.NON_NULL)
        AutostopConfig autostopConfig) {}

package ru.kostyukov.tankgenerator.models.yaml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record PhantomConfig(
    @JsonProperty("address") String address,
    @JsonProperty("ammofile") String ammofile,
    @JsonProperty("ammo_type") String ammoType,
    @JsonProperty("load_profile") LoadProfile loadProfile,
    @JsonProperty("instances") @JsonInclude(JsonInclude.Include.NON_NULL) Integer instances,
    @JsonProperty("headers") List<String> headers) {}

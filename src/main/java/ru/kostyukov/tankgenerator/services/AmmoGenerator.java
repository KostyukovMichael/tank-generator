package ru.kostyukov.tankgenerator.services;

import java.util.List;
import ru.kostyukov.tankgenerator.dto.GenerationRequest;
import ru.kostyukov.tankgenerator.models.Endpoint;

public interface AmmoGenerator {
  String generateAmmo(List<Endpoint> endpoints, GenerationRequest generationRequest);
}

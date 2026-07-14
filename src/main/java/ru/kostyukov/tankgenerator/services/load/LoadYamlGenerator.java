package ru.kostyukov.tankgenerator.services.load;

import ru.kostyukov.tankgenerator.dto.GenerationRequest;

public interface LoadYamlGenerator {
  String generateLoadYaml(GenerationRequest generationRequest);
}

package ru.kostyukov.tankgenerator.services.readme;

import ru.kostyukov.tankgenerator.dto.GenerationRequest;

public interface ReadmeGenerator {
  String generateReadme(GenerationRequest generationRequest);
}

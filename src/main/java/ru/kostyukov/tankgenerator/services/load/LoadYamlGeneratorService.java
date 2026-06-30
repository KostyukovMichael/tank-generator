package ru.kostyukov.tankgenerator.services.load;

import org.springframework.stereotype.Service;
import ru.kostyukov.tankgenerator.dto.GenerationRequest;

@Service
public class LoadYamlGeneratorService implements LoadYamlGenerator {

  // TODO добавить поддержку нелинейной нагрузки
  @Override
  public String generateLoadYaml(GenerationRequest generationRequest) {
    String content =
        """
        phantom:
          address: %s
          ammofile: ammo.txt
          ammo_type: phantom
          load_profile:
            load_type: rps
            schedule: line(1, %d, %s)
          headers:
          - "[User-Agent: Tank]"
        """;

    return content.formatted(
        generationRequest.getTargetHost(),
        generationRequest.getRps(),
        generationRequest.getDuration());
  }
}

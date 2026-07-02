package ru.kostyukov.tankgenerator.services.readme;

import org.springframework.stereotype.Service;
import ru.kostyukov.tankgenerator.dto.GenerationRequest;

@Service
public class ReadmeGeneratorService implements ReadmeGenerator {
  @Override
  public String generateReadme(GenerationRequest generationRequest) {

    return """
    # Инструкция по запуску нагрузочного теста с помощью yandex-tank

    Стандартный запуск происходит через Docker:

    ```bash
    docker run \\
      -v $(pwd):/var/loadtest \\
      -it yandex/yandex-tank
    ```
    """;
  }
}

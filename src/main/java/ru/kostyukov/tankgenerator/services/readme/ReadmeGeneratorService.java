package ru.kostyukov.tankgenerator.services.readme;

import org.springframework.stereotype.Service;
import ru.kostyukov.tankgenerator.dto.GenerationRequest;

@Service
public class ReadmeGeneratorService implements ReadmeGenerator {
  @Override
  public String generateReadme(GenerationRequest generationRequest) {

    // TODO или пусть сам разбирается?
    String content =
        """
        # Инструкция по запуску нагрузочного теста с помощью Yandex.Tank

        Конфигурационные файлы для тестируемого хоста `%s` успешно сгенерированы.

        ## Архив содержит:
        - `ammo.txt` - файл с запросами.
        - `load.yaml` - конфигурационный файл для запуска Yandex.Tank.

        ## Запуск тестирования:
        Стандартный запуск происходит через Docker:

        ```bash
        docker run \\\\
          -v $(pwd):/var/loadtest \\\\
          --net="host" \\\\
          -it yandex/yandex-tank
        ```
        """;

    return content.formatted(generationRequest.getTargetHost());
  }
}

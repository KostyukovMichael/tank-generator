package ru.kostyukov.tankgenerator.services;

import org.springframework.stereotype.Service;
import ru.kostyukov.tankgenerator.dto.GenerationRequest;

@Service
public class ConfigGeneratorService {

  // TODO добавить поддержку нелинейной нагрузки
  public String generateLoadYaml(GenerationRequest generationRequest) {
    return
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
"""
        .formatted(
            generationRequest.getTargetHost(),
            generationRequest.getRps(),
            generationRequest.getDuration());
  }

  public String generateReadMe(GenerationRequest generationRequest) {
    return
"""
# Инструкция по запуску нагрузочного теста с помощью Yandex.Tank

Конфигурационные файлы для тестируемого хоста `%s` успешно сгенерированы.

## Архив содержит:
- `ammo.txt` - файл с запросами.
- `load.yaml` - конфигурационный файл для запуска Yandex.Tank.

## Запуск тестирования:
Стандартный запуск происходит через Docker:

```bash
docker run \\
  -v $(pwd):/var/loadtest \\
  --net="host" \\
  -it yandex/load-tank
```
"""
        .formatted(generationRequest.getTargetHost());
  }
}

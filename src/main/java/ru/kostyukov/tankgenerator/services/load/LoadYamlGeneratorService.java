package ru.kostyukov.tankgenerator.services.load;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.util.List;
import org.springframework.stereotype.Service;
import ru.kostyukov.tankgenerator.dto.GenerationRequest;
import ru.kostyukov.tankgenerator.models.yaml.*;

@Service
public class LoadYamlGeneratorService implements LoadYamlGenerator {

  private final YAMLMapper yamlMapper = new YAMLMapper();

  // TODO добавить поддержку нелинейной нагрузки
  @Override
  public String generateLoadYaml(GenerationRequest generationRequest) {
    Schedule schedule =
        new LinearSchedule(1, generationRequest.getRps(), generationRequest.getDuration());

    LoadProfile loadProfile = new LoadProfile("rps", schedule);

    PhantomConfig phantomConfig =
        new PhantomConfig(
            generationRequest.getTargetHost(),
            "ammo.txt",
            "phantom",
            loadProfile,
            List.of("[User-Agent: Tank]"));

    TankConfig tankConfig = new TankConfig(phantomConfig);

    try {
      return yamlMapper.writeValueAsString(tankConfig);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("in LoadYamlGenerator: ", e);
    }
  }
}

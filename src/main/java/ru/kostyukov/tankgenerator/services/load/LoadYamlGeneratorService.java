package ru.kostyukov.tankgenerator.services.load;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.util.List;
import org.springframework.stereotype.Service;
import ru.kostyukov.tankgenerator.config.TankGeneratorProperties;
import ru.kostyukov.tankgenerator.dto.GenerationRequest;
import ru.kostyukov.tankgenerator.exceptions.YamlGenerationException;
import ru.kostyukov.tankgenerator.models.yaml.*;

@Service
public class LoadYamlGeneratorService implements LoadYamlGenerator {
  private final YAMLMapper yamlMapper = new YAMLMapper();
  private final TankGeneratorProperties properties;
  private final ScheduleFactory scheduleFactory;

  public LoadYamlGeneratorService(
      TankGeneratorProperties properties, ScheduleFactory scheduleFactory) {
    this.properties = properties;
    this.scheduleFactory = scheduleFactory;
  }

  @Override
  public String generateLoadYaml(GenerationRequest generationRequest) {
    String loadType =
        generationRequest.getLoadType() != null
            ? generationRequest.getLoadType()
            : properties.getDefaultLoadType();

    Schedule schedule = scheduleFactory.createSchedule(generationRequest);

    LoadProfile loadProfile = new LoadProfile(loadType, schedule);

    PhantomConfig phantomConfig =
        new PhantomConfig(
            generationRequest.getTargetHost(),
            "ammo.txt",
            "phantom",
            loadProfile,
            generationRequest.getInstances(),
            List.of("[User-Agent: Tank]"));

    TankConfig tankConfig = new TankConfig(phantomConfig);

    try {
      return yamlMapper.writeValueAsString(tankConfig);
    } catch (JsonProcessingException e) {
      throw new YamlGenerationException("error while writing config", e);
    }
  }
}

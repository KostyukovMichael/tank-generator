package ru.kostyukov.tankgenerator.services.load;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.util.List;
import org.springframework.stereotype.Service;
import ru.kostyukov.tankgenerator.config.TankGeneratorProperties;
import ru.kostyukov.tankgenerator.dto.GenerationRequest;
import ru.kostyukov.tankgenerator.exceptions.YamlGenerationException;
import ru.kostyukov.tankgenerator.models.yaml.*;
import ru.kostyukov.tankgenerator.models.yaml.autostop.AutostopConfig;
import ru.kostyukov.tankgenerator.models.yaml.phantom.LoadProfile;
import ru.kostyukov.tankgenerator.models.yaml.phantom.PhantomConfig;
import ru.kostyukov.tankgenerator.models.yaml.phantom.Schedule;

@Service
public class LoadYamlGeneratorService implements LoadYamlGenerator {
  private final YAMLMapper yamlMapper = new YAMLMapper();
  private final TankGeneratorProperties properties;
  private final ScheduleFactory scheduleFactory;
  private final AutostopFactory autostopFactory;

  public LoadYamlGeneratorService(
      TankGeneratorProperties properties,
      ScheduleFactory scheduleFactory,
      AutostopFactory autostopFactory) {
    this.properties = properties;
    this.scheduleFactory = scheduleFactory;
    this.autostopFactory = autostopFactory;
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

    AutostopConfig autostopConfig = autostopFactory.createAutostopConfig(generationRequest);

    InfluxConfig influxConfig = null;
    if (properties.isDefaultInfluxEnabled()) {
      influxConfig =
          new InfluxConfig(
              true,
              properties.getDefaultInfluxAddress(),
              properties.getDefaultInfluxPort(),
              properties.getDefaultInfluxDatabase(),
              properties.getDefaultInfluxTag());
    }

    TankConfig tankConfig = new TankConfig(phantomConfig, autostopConfig, influxConfig);

    try {
      return yamlMapper.writeValueAsString(tankConfig);
    } catch (JsonProcessingException e) {
      throw new YamlGenerationException("error while writing config", e);
    }
  }
}

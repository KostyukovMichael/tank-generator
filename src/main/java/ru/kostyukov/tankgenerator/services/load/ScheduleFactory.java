package ru.kostyukov.tankgenerator.services.load;

import org.springframework.stereotype.Component;
import ru.kostyukov.tankgenerator.config.TankGeneratorProperties;
import ru.kostyukov.tankgenerator.dto.GenerationRequest;
import ru.kostyukov.tankgenerator.exceptions.YamlGenerationException;
import ru.kostyukov.tankgenerator.models.yaml.phantom.ConstSchedule;
import ru.kostyukov.tankgenerator.models.yaml.phantom.LinearSchedule;
import ru.kostyukov.tankgenerator.models.yaml.phantom.Schedule;
import ru.kostyukov.tankgenerator.models.yaml.phantom.StepSchedule;

@Component
public class ScheduleFactory {
  private final TankGeneratorProperties properties;

  public ScheduleFactory(TankGeneratorProperties properties) {
    this.properties = properties;
  }

  public Schedule createSchedule(GenerationRequest generationRequest) {
    String type =
        generationRequest.getSchedule() != null
            ? generationRequest.getSchedule()
            : properties.getDefaultSchedule();

    int rps =
        generationRequest.getRps() != null
            ? generationRequest.getRps()
            : properties.getDefaultRps();

    int rpsStart =
        generationRequest.getRpsStart() != null
            ? generationRequest.getRpsStart()
            : properties.getDefaultRpsStart();

    int rpsEnd =
        generationRequest.getRpsEnd() != null
            ? generationRequest.getRpsEnd()
            : properties.getDefaultRpsEnd();

    int rpsStep =
        generationRequest.getRpsStep() != null
            ? generationRequest.getRpsStep()
            : properties.getDefaultRpsStep();

    String duration =
        generationRequest.getDuration() != null
            ? generationRequest.getDuration()
            : properties.getDefaultDuration();

    return switch (type) {
      case "const" -> new ConstSchedule(rps, duration);
      case "line" -> new LinearSchedule(rpsStart, rpsEnd, duration);
      case "step" -> new StepSchedule(rpsStart, rpsEnd, rpsStep, duration);
      default -> throw new YamlGenerationException("unexpected load type " + type);
    };
  }
}

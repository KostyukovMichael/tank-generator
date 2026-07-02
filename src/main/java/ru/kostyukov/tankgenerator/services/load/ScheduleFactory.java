package ru.kostyukov.tankgenerator.services.load;

import ru.kostyukov.tankgenerator.dto.GenerationRequest;
import ru.kostyukov.tankgenerator.models.yaml.ConstSchedule;
import ru.kostyukov.tankgenerator.models.yaml.LinearSchedule;
import ru.kostyukov.tankgenerator.models.yaml.Schedule;
import ru.kostyukov.tankgenerator.models.yaml.StepSchedule;

public class ScheduleFactory {
  private static final String DEFAULT_SCHEDULE = "line";
  private static final int DEFAULT_RPS = 50;
  private static final int DEFAULT_RPS_START = 1;
  private static final int DEFAULT_RPS_END = 100;
  private static final int DEFAULT_RPS_STEP = 10;
  private static final String DEFAULT_DURATION = "60s";

  public static Schedule createSchedule(GenerationRequest generationRequest) {
    String type =
        generationRequest.getSchedule() != null
            ? generationRequest.getSchedule()
            : DEFAULT_SCHEDULE;

    int rps = generationRequest.getRps() != null ? generationRequest.getRps() : DEFAULT_RPS;

    int rpsStart =
        generationRequest.getRpsStart() != null
            ? generationRequest.getRpsStart()
            : DEFAULT_RPS_START;

    int rpsEnd =
        generationRequest.getRpsEnd() != null ? generationRequest.getRpsEnd() : DEFAULT_RPS_END;

    int rpsStep =
        generationRequest.getRpsStep() != null ? generationRequest.getRpsStep() : DEFAULT_RPS_STEP;

    String duration =
        generationRequest.getDuration() != null
            ? generationRequest.getDuration()
            : DEFAULT_DURATION;

    return switch (type) {
      case "const" -> new ConstSchedule(rps, duration);
      case "line" -> new LinearSchedule(rpsStart, rpsEnd, duration);
      case "step" -> new StepSchedule(rpsStart, rpsEnd, rpsStep, duration);
      default -> throw new IllegalArgumentException("in ScheduleFactory: wrong load type " + type);
    };
  }
}

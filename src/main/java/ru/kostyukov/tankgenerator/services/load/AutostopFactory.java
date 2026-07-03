package ru.kostyukov.tankgenerator.services.load;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.kostyukov.tankgenerator.config.TankGeneratorProperties;
import ru.kostyukov.tankgenerator.dto.GenerationRequest;
import ru.kostyukov.tankgenerator.models.yaml.autostop.*;

@Component
public class AutostopFactory {
  private final TankGeneratorProperties properties;

  public AutostopFactory(TankGeneratorProperties properties) {
    this.properties = properties;
  }

  public AutostopConfig createAutostopConfig(GenerationRequest generationRequest) {
    boolean autostopEnabled =
        generationRequest.getAutostopEnabled() != null
            ? generationRequest.getAutostopEnabled()
            : properties.isDefaultAutostopEnabled();
    if (!autostopEnabled) {
      return null;
    }

    boolean isHttpRequested = isHttpRequested(generationRequest);
    boolean isNetRequested = isNetRequested(generationRequest);
    boolean isTimeRequested = isTimeRequested(generationRequest);
    boolean useDefaults = !isHttpRequested && !isNetRequested && !isTimeRequested;

    List<Condition> conditions = new ArrayList<>();

    if (useDefaults || isHttpRequested) {
      conditions.add(buildHttpCondition(generationRequest));
    }

    if (useDefaults || isNetRequested) {
      conditions.add(buildNetCondition(generationRequest));
    }

    if (useDefaults || isTimeRequested) {
      conditions.add(buildTimeCondition(generationRequest));
    }

    return new AutostopConfig(conditions);
  }

  private boolean isHttpRequested(GenerationRequest request) {
    return request.getAutostopHttpCode() != null
        || request.getAutostopHttpLimit() != null
        || request.getAutostopHttpWindow() != null;
  }

  private boolean isNetRequested(GenerationRequest request) {
    return request.getAutostopNetCode() != null
        || request.getAutostopNetLimit() != null
        || request.getAutostopNetWindow() != null;
  }

  private boolean isTimeRequested(GenerationRequest request) {
    return request.getAutostopResponseTime() != null || request.getAutostopTimeWindow() != null;
  }

  private HttpCondition buildHttpCondition(GenerationRequest request) {
    String httpCode =
        request.getAutostopHttpCode() != null
            ? request.getAutostopHttpCode()
            : properties.getDefaultAutostopHttpCode();
    String httpLimit =
        request.getAutostopHttpLimit() != null
            ? request.getAutostopHttpLimit()
            : properties.getDefaultAutostopHttpLimit();
    String httpWindow =
        request.getAutostopHttpWindow() != null
            ? request.getAutostopHttpWindow()
            : properties.getDefaultAutostopHttpWindow();

    return new HttpCondition(httpCode, httpLimit, httpWindow);
  }

  private NetCondition buildNetCondition(GenerationRequest request) {
    String netCode =
        request.getAutostopNetCode() != null
            ? request.getAutostopNetCode()
            : properties.getDefaultAutostopNetCode();
    String netLimit =
        request.getAutostopNetLimit() != null
            ? request.getAutostopNetLimit()
            : properties.getDefaultAutostopNetLimit();
    String netWindow =
        request.getAutostopNetWindow() != null
            ? request.getAutostopNetWindow()
            : properties.getDefaultAutostopNetWindow();

    return new NetCondition(netCode, netLimit, netWindow);
  }

  private TimeCondition buildTimeCondition(GenerationRequest request) {
    String responseTime =
        request.getAutostopResponseTime() != null
            ? request.getAutostopResponseTime()
            : properties.getDefaultAutostopResponseTime();
    String timeWindow =
        request.getAutostopTimeWindow() != null
            ? request.getAutostopTimeWindow()
            : properties.getDefaultAutostopTimeWindow();

    return new TimeCondition(responseTime, timeWindow);
  }
}

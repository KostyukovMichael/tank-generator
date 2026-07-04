package ru.kostyukov.tankgenerator.services.load;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
    return StringUtils.hasText(request.getAutostopHttpCode())
        || StringUtils.hasText(request.getAutostopHttpLimit())
        || StringUtils.hasText(request.getAutostopHttpWindow());
  }

  private boolean isNetRequested(GenerationRequest request) {
    return StringUtils.hasText(request.getAutostopNetCode())
        || StringUtils.hasText(request.getAutostopNetLimit())
        || StringUtils.hasText(request.getAutostopNetWindow());
  }

  private boolean isTimeRequested(GenerationRequest request) {
    return StringUtils.hasText(request.getAutostopResponseTime())
        || StringUtils.hasText(request.getAutostopTimeWindow());
  }

  private HttpCondition buildHttpCondition(GenerationRequest request) {
    String httpCode =
        StringUtils.hasText(request.getAutostopHttpCode())
            ? request.getAutostopHttpCode()
            : properties.getDefaultAutostopHttpCode();
    String httpLimit =
        StringUtils.hasText(request.getAutostopHttpLimit())
            ? request.getAutostopHttpLimit()
            : properties.getDefaultAutostopHttpLimit();
    String httpWindow =
        StringUtils.hasText(request.getAutostopHttpWindow())
            ? request.getAutostopHttpWindow()
            : properties.getDefaultAutostopHttpWindow();

    return new HttpCondition(httpCode, httpLimit, httpWindow);
  }

  private NetCondition buildNetCondition(GenerationRequest request) {
    String netCode =
        StringUtils.hasText(request.getAutostopNetCode())
            ? request.getAutostopNetCode()
            : properties.getDefaultAutostopNetCode();
    String netLimit =
        StringUtils.hasText(request.getAutostopNetLimit())
            ? request.getAutostopNetLimit()
            : properties.getDefaultAutostopNetLimit();
    String netWindow =
        StringUtils.hasText(request.getAutostopNetWindow())
            ? request.getAutostopNetWindow()
            : properties.getDefaultAutostopNetWindow();

    return new NetCondition(netCode, netLimit, netWindow);
  }

  private TimeCondition buildTimeCondition(GenerationRequest request) {
    String responseTime =
        StringUtils.hasText(request.getAutostopResponseTime())
            ? request.getAutostopResponseTime()
            : properties.getDefaultAutostopResponseTime();
    String timeWindow =
        StringUtils.hasText(request.getAutostopTimeWindow())
            ? request.getAutostopTimeWindow()
            : properties.getDefaultAutostopTimeWindow();

    return new TimeCondition(responseTime, timeWindow);
  }
}

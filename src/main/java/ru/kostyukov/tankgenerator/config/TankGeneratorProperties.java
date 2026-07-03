package ru.kostyukov.tankgenerator.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties
public class TankGeneratorProperties {
  private final String defaultUserAgent = "Tank";
  private final String defaultReplaceParameter = "1";
  private final String defaultLoadType = "rps";
  private final String defaultSchedule = "line";
  private final int defaultRps = 50;
  private final int defaultRpsStart = 1;
  private final int defaultRpsEnd = 100;
  private final int defaultRpsStep = 10;
  private final String defaultDuration = "60s";
  private final boolean defaultAutostopEnabled = false;
  private final String defaultAutostopHttpCode = "5xx";
  private final String defaultAutostopHttpLimit = "25%";
  private final String defaultAutostopHttpWindow = "10s";
  private final String defaultAutostopNetCode = "xx";
  private final String defaultAutostopNetLimit = "25";
  private final String defaultAutostopNetWindow = "10s";
  private final String defaultAutostopResponseTime = "1500ms";
  private final String defaultAutostopTimeWindow = "10s";
}

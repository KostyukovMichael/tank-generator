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
}

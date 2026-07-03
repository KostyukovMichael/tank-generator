package ru.kostyukov.tankgenerator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("tank")
public class TankGeneratorProperties {
  private String defaultUserAgent = "Tank";
  private String defaultReplaceParameter = "1";
  private String defaultLoadType = "rps";
  private String defaultSchedule = "line";
  private int defaultRps = 50;
  private int defaultRpsStart = 1;
  private int defaultRpsEnd = 100;
  private int defaultRpsStep = 10;
  private String defaultDuration = "60s";
  private boolean defaultAutostopEnabled = false;
  private String defaultAutostopHttpCode = "5xx";
  private String defaultAutostopHttpLimit = "25%";
  private String defaultAutostopHttpWindow = "10s";
  private String defaultAutostopNetCode = "xx";
  private String defaultAutostopNetLimit = "25";
  private String defaultAutostopNetWindow = "10s";
  private String defaultAutostopResponseTime = "1500ms";
  private String defaultAutostopTimeWindow = "10s";
}

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
  private String defaultDuration = "60s";
  private String defaultAutostopHttpCode = "5xx";
  private String defaultAutostopHttpLimit = "25%";
  private String defaultAutostopHttpWindow = "10s";
  private String defaultAutostopNetCode = "xx";
  private String defaultAutostopNetLimit = "25";
  private String defaultAutostopNetWindow = "10s";
  private String defaultAutostopResponseTime = "1500ms";
  private String defaultAutostopTimeWindow = "10s";
  private String defaultInfluxAddress = "influxdb";
  private String defaultInfluxDatabase = "influx";
  private String defaultInfluxTag = "influx-tank";
  private int defaultRps = 50;
  private int defaultRpsStart = 1;
  private int defaultRpsEnd = 100;
  private int defaultRpsStep = 10;
  private int defaultInfluxPort = 8086;
  // TODO добавить этот параметр на фронтенд
  private int defaultAmmoVariationsCount = 25;
  private boolean defaultAutostopEnabled = false;
  private boolean defaultInfluxEnabled = true;
}

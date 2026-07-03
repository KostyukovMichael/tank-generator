package ru.kostyukov.tankgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerationRequest {
  private String targetHost;
  private String duration;
  private String authToken;
  private String loadType;
  private String schedule;
  private Integer rps;
  private Integer rpsStart;
  private Integer rpsEnd;
  private Integer rpsStep;
  private Integer instances;

  private Boolean autostopEnabled;
  private String autostopHttpCode;
  private String autostopHttpLimit;
  private String autostopHttpWindow;
  private String autostopNetCode;
  private String autostopNetLimit;
  private String autostopNetWindow;
  private String autostopResponseTime;
  private String autostopTimeWindow;
}

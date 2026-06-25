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
  private int rps;
}

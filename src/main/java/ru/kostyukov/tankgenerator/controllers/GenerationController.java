package ru.kostyukov.tankgenerator.controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.kostyukov.tankgenerator.dto.GenerationRequest;
import ru.kostyukov.tankgenerator.models.Endpoint;
import ru.kostyukov.tankgenerator.services.AmmoGeneratorService;
import ru.kostyukov.tankgenerator.services.ConfigGeneratorService;
import ru.kostyukov.tankgenerator.services.OpenApiParserService;

@RestController
public class GenerationController {
  private final OpenApiParserService openApiParserService;
  private final AmmoGeneratorService ammoGeneratorService;
  private final ConfigGeneratorService configGeneratorService;

  public GenerationController(
      OpenApiParserService openApiParserService,
      AmmoGeneratorService ammoGeneratorService,
      ConfigGeneratorService configGeneratorService) {
    this.openApiParserService = openApiParserService;
    this.ammoGeneratorService = ammoGeneratorService;
    this.configGeneratorService = configGeneratorService;
  }

  @PostMapping(value = "/generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<byte[]> generate(
      @RequestParam("file") MultipartFile file, @ModelAttribute GenerationRequest generationRequest)
      throws IOException {

    if (file.isEmpty()) {
      return ResponseEntity.badRequest()
          .body("загружаемый файл OpenApi пуст".getBytes(StandardCharsets.UTF_8));
    }

    String openApiContent = new String(file.getBytes(), StandardCharsets.UTF_8);

    List<Endpoint> endpoints = openApiParserService.parseOpenApi(openApiContent);

    String ammo = ammoGeneratorService.generateAmmo(endpoints, generationRequest);
    String loadYaml = configGeneratorService.generateLoadYaml(generationRequest);
    String readme = configGeneratorService.generateReadMe(generationRequest);

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"tank-configure.zip\"")
        .body(zipFiles(ammo, loadYaml, readme));
  }

  private byte[] zipFiles(String ammo, String loadYaml, String readme) {}
}

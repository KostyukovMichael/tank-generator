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
import ru.kostyukov.tankgenerator.services.ammo.AmmoGenerator;
import ru.kostyukov.tankgenerator.services.archive.ArchiveService;
import ru.kostyukov.tankgenerator.services.load.LoadYamlGenerator;
import ru.kostyukov.tankgenerator.services.parse.OpenApiParserService;
import ru.kostyukov.tankgenerator.services.readme.ReadmeGenerator;

@RestController
public class GenerationController {
  private final OpenApiParserService openApiParserService;
  private final AmmoGenerator ammoGenerator;
  private final LoadYamlGenerator loadYamlGenerator;
  private final ReadmeGenerator readmeGenerator;
  private final ArchiveService archiveService;

  public GenerationController(
      OpenApiParserService openApiParserService,
      AmmoGenerator ammoGenerator,
      LoadYamlGenerator loadYamlGenerator,
      ReadmeGenerator readmeGenerator,
      ArchiveService archiveService) {
    this.openApiParserService = openApiParserService;
    this.ammoGenerator = ammoGenerator;
    this.loadYamlGenerator = loadYamlGenerator;
    this.readmeGenerator = readmeGenerator;
    this.archiveService = archiveService;
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

    String ammo = ammoGenerator.generateAmmo(endpoints, generationRequest);
    String loadYaml = loadYamlGenerator.generateLoadYaml(generationRequest);
    String readme = readmeGenerator.generateReadme(generationRequest);

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"tank-configure.zip\"")
        .body(archiveService.zipFiles(ammo, loadYaml, readme));
  }
}

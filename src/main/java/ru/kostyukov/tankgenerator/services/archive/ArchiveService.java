package ru.kostyukov.tankgenerator.services.archive;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ArchiveService {

  @Value("classpath:content/README.md")
  private Resource readmeResource;

  @Value("classpath:content/run.sh")
  private Resource runShResource;

  @Value("classpath:content/run.bat")
  private Resource runBatResource;

  @Value("classpath:content/docker-compose.yaml")
  private Resource dockerComposeResource;

  @Value("classpath:content/grafana/provisioning/datasources/datasource.yaml")
  private Resource datasourceResource;

  @Value("classpath:content/grafana/provisioning/dashboards/dashboards.yaml")
  private Resource dashboardsResource;

  @Value("classpath:content/grafana/provisioning/dashboards/dashboard.json")
  private Resource dashboardJsonResource;

  public byte[] zipFiles(String ammo, String loadYaml) throws IOException {
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

    try (ZipOutputStream zipOut = new ZipOutputStream(byteOut)) {
      writeEntry(zipOut, "ammo.txt", ammo.getBytes(StandardCharsets.UTF_8));
      writeEntry(zipOut, "load.yaml", loadYaml.getBytes(StandardCharsets.UTF_8));
      writeEntry(zipOut, "README.md", readmeResource.getContentAsByteArray());
      writeEntry(zipOut, "run.sh", runShResource.getContentAsByteArray());
      writeEntry(zipOut, "run.bat", runBatResource.getContentAsByteArray());
      writeEntry(zipOut, "docker-compose.yaml", dockerComposeResource.getContentAsByteArray());
      writeEntry(
          zipOut,
          "grafana/provisioning/datasources/datasource.yaml",
          datasourceResource.getContentAsByteArray());
      writeEntry(
          zipOut,
          "grafana/provisioning/dashboards/dashboards.yaml",
          dashboardsResource.getContentAsByteArray());
      writeEntry(
          zipOut,
          "grafana/provisioning/dashboards/dashboard.json",
          dashboardJsonResource.getContentAsByteArray());

      zipOut.finish();
    }

    return byteOut.toByteArray();
  }

  private void writeEntry(ZipOutputStream zipOut, String fileName, byte[] content)
      throws IOException {
    var zipEntry = new ZipEntry(fileName);
    zipOut.putNextEntry(zipEntry);
    zipOut.write(content);
    zipOut.closeEntry();
  }
}

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

  public byte[] zipFiles(String ammo, String loadYaml) throws IOException {
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

    try (ZipOutputStream zipOut = new ZipOutputStream(byteOut)) {
      ZipEntry ammoEntry = new ZipEntry("ammo.txt");
      zipOut.putNextEntry(ammoEntry);
      zipOut.write(ammo.getBytes(StandardCharsets.UTF_8));
      zipOut.closeEntry();

      ZipEntry loadYamlEntry = new ZipEntry("load.yaml");
      zipOut.putNextEntry(loadYamlEntry);
      zipOut.write(loadYaml.getBytes(StandardCharsets.UTF_8));
      zipOut.closeEntry();

      ZipEntry readmeEntry = new ZipEntry("README.md");
      zipOut.putNextEntry(readmeEntry);
      zipOut.write(readmeResource.getContentAsByteArray());
      zipOut.closeEntry();

      ZipEntry runShEntry = new ZipEntry("run.sh");
      zipOut.putNextEntry(runShEntry);
      zipOut.write(runShResource.getContentAsByteArray());
      zipOut.closeEntry();

      ZipEntry runBatEntry = new ZipEntry("run.bat");
      zipOut.putNextEntry(runBatEntry);
      zipOut.write(runBatResource.getContentAsByteArray());
      zipOut.closeEntry();

      zipOut.finish();
    }

    return byteOut.toByteArray();
  }
}

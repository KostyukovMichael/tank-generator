package ru.kostyukov.tankgenerator.services.archive;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.stereotype.Service;

@Service
public class ArchiveService {
  public byte[] zipFiles(String ammo, String loadYaml, String readme) throws IOException {
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
      zipOut.write(readme.getBytes(StandardCharsets.UTF_8));
      zipOut.closeEntry();

      zipOut.finish();
    }

    return byteOut.toByteArray();
  }
}

package ru.kostyukov.tankgenerator.services.ammo;

import ru.kostyukov.tankgenerator.exceptions.AmmoGenerationException;

class TagGenerator {
  public static String generateTag(String method, String path) {
    if (method == null || path == null) {
      throw new AmmoGenerationException("while generate tag: variable `method` or `path` is null");
    }

    return method + "_" + path.replace("/", "_").toLowerCase().replaceAll("[^a-z0-9_]+", "");
  }
}

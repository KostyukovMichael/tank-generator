package ru.kostyukov.tankgenerator.services.ammo;

import ru.kostyukov.tankgenerator.exceptions.AmmoGenerationException;

final class TagGenerator {
  private TagGenerator() {
    throw new AssertionError("utility class doesn't need an instance");
  }

  static String generateTag(String method, String path) {
    if (method == null || path == null) {
      throw new AmmoGenerationException("while generate tag: variable `method` or `path` is null");
    }

    return method
        + "_"
        + path.replace("/", "_").toLowerCase().replaceAll("[^a-z0-9_]+", "").replaceAll("_+", "_");
  }
}

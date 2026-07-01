package ru.kostyukov.tankgenerator.services.ammo;

class TagGenerator {
  public static String generateTag(String method, String path) {
    if (method == null || path == null) {
      throw new IllegalArgumentException("in TagGenerator in generate tag: method or path is null");
    }

    return method + "_" + path.replace("/", "_").toLowerCase().replaceAll("[^a-z0-9_]+", "");
  }
}

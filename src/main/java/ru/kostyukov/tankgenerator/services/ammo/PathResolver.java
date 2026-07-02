package ru.kostyukov.tankgenerator.services.ammo;

final class PathResolver {
  private static final String REPLACE_MOCK = "1";

  private PathResolver() {
    throw new AssertionError("utility class doesn't need an instance");
  }

  static String resolvePath(String path) {
    if (path == null) {
      return "";
    }

    return path.replaceAll("\\{[^}]+}", REPLACE_MOCK);
  }
}

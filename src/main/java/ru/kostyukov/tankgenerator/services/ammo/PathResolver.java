package ru.kostyukov.tankgenerator.services.ammo;

final class PathResolver {
  private PathResolver() {
    throw new AssertionError("utility class doesn't need an instance");
  }

  static String resolvePath(String path, String replaceParameter) {
    if (path == null) {
      return "";
    }

    return path.replaceAll("\\{[^}]+}", replaceParameter);
  }
}

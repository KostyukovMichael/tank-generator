package ru.kostyukov.tankgenerator.services.ammo;

class PathResolver {
  private static final String REPLACE_MOCK = "1";

  public static String resolvePath(String path) {
    if (path == null) {
      return "";
    }

    return path.replaceAll("\\{[^}]+}", REPLACE_MOCK);
  }
}

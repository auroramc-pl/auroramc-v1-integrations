package pl.auroramc.integrations.dsl;

import java.nio.file.Path;

final class DiscoveryUtils {

  private static final String GROOVY_FILE_EXTENSION = ".groovy";

  private DiscoveryUtils() {}

  public static boolean isGroovyFile(final Path filePath) {
    return filePath.getFileName().toString().endsWith(GROOVY_FILE_EXTENSION);
  }
}

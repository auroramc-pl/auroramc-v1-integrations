package pl.auroramc.integrations.dsl;

import static java.beans.Introspector.decapitalize;

import groovy.lang.Binding;
import java.nio.file.Path;
import java.util.Set;

public final class DiscoveryUtils {

  private static final String GROOVY_FILE_EXTENSION = ".groovy";

  private DiscoveryUtils() {}

  public static Binding getGeneratedBinding(final Set<Object> variables) {
    final Binding binding = new Binding();
    for (final Object variable : variables) {
      binding.setVariable(decapitalize(variable.getClass().getSimpleName()), variable);
    }
    return binding;
  }

  public static boolean isGroovyFile(final Path filePath) {
    return filePath.getFileName().toString().endsWith(GROOVY_FILE_EXTENSION);
  }
}

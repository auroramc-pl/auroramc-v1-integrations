package pl.auroramc.integrations.dsl;

import static java.nio.file.Files.walk;
import static java.util.stream.Collectors.toUnmodifiableSet;

import groovy.lang.GroovyShell;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Stream;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

abstract class DiscoveryService<T> implements DiscoveryFacade<T> {

  protected final String parentClosure;
  protected final Class<T> elementType;
  private final ClassLoader parentClassLoader;
  private final GroovyShell shell;

  protected DiscoveryService(
      final ClassLoader parentClassLoader, final String parentClosure, final Class<T> elementType) {
    this.parentClassLoader = parentClassLoader;
    this.parentClosure = parentClosure;
    this.elementType = elementType;
    this.shell = getDefaultShell();
  }

  public abstract ImportCustomizer getImportCustomizer();

  @Override
  public Set<T> getElementDefinitions(final Path traversalPath) {
    try (final Stream<Path> pathStream = walk(traversalPath)) {
      return pathStream
          .filter(Files::isRegularFile)
          .filter(DiscoveryUtils::isGroovyFile)
          .map(this::getElementDefinition)
          .collect(toUnmodifiableSet());
    } catch (final Exception exception) {
      throw new DiscoveryDiscoveringException(
          "Could not discover elements in %s path, because of unexpected exception."
              .formatted(traversalPath.toString()),
          exception);
    }
  }

  private T getElementDefinition(final Path definitionPath) {
    try (final InputStream inputStream = Files.newInputStream(definitionPath);
        final InputStreamReader reader = new InputStreamReader(inputStream)) {
      final Object evaluatedObject = shell.evaluate(reader);
      if (evaluatedObject == null) {
        throw new DiscoveryDiscoveringException(
            "Could not parse element definition from %s path, because of null result."
                .formatted(definitionPath.toString()));
      }

      if (elementType.isInstance(evaluatedObject)) {
        return elementType.cast(evaluatedObject);
      }

      throw new DiscoveryDiscoveringException(
          "Could not parse element definition from %s path, because of unexpected type."
              .formatted(definitionPath.toString()));
    } catch (final Exception exception) {
      throw new DiscoveryDiscoveringException(
          "Could not parse element definition from %s path, because of unexpected exception."
              .formatted(definitionPath.toString()),
          exception);
    }
  }

  public GroovyShell getDefaultShell() {
    final CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
    compilerConfiguration.addCompilationCustomizers(getImportCustomizer());
    return new GroovyShell(parentClassLoader, compilerConfiguration);
  }
}

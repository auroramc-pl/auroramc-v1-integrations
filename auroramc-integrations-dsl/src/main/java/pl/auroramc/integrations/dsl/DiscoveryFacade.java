package pl.auroramc.integrations.dsl;

import groovy.lang.Binding;
import java.nio.file.Path;
import java.util.Set;

public interface DiscoveryFacade<T> {

  Set<T> getElementDefinitions(final Path traversalPath);

  default Binding getBinding() {
    return new Binding();
  }
}

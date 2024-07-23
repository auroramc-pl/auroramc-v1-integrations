package pl.auroramc.integrations.dsl;

import java.nio.file.Path;
import java.util.Set;

public interface DiscoveryFacade<T> {

  Set<T> getElementDefinitions(final Path traversalPath);
}

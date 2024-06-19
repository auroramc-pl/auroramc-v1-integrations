package pl.auroramc.integrations.dsl;

class DiscoveryDiscoveringException extends IllegalStateException {

  DiscoveryDiscoveringException(final String message, final Throwable cause) {
    super(message, cause);
  }

  DiscoveryDiscoveringException(final String message) {
    super(message);
  }
}

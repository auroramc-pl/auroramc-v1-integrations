package pl.auroramc.integrations.configs.serdes.juliet;

import eu.okaeri.configs.serdes.SerdesRegistry;
import org.jetbrains.annotations.NotNull;
import pl.auroramc.integrations.configs.serdes.SerdesCommons;

public class SerdesJuliet extends SerdesCommons {

  @Override
  public void register(final @NotNull SerdesRegistry registry) {
    super.register(registry);
    registry.register(new HikariConfigSerializer());
  }
}
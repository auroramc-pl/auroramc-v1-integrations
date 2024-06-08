package pl.auroramc.integrations.configs.serdes.message;

import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import org.jetbrains.annotations.NotNull;

public class SerdesMessages implements OkaeriSerdesPack {

  @Override
  public void register(final @NotNull SerdesRegistry registry) {
    registry.register(new StringToMutableMessageTransformer());
  }
}

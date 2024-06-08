package pl.auroramc.integrations.configs.serdes.standard;

import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import org.jetbrains.annotations.NotNull;

public class SerdesStandard implements OkaeriSerdesPack {

  @Override
  public void register(final @NotNull SerdesRegistry registry) {
    registry.register(new PropertiesSerializer());
    registry.register(new StringToDecimalFormatTransformer());
    registry.register(new StringToLocaleTransformer());
    registry.register(new StringToZoneIdTransformer());
  }
}

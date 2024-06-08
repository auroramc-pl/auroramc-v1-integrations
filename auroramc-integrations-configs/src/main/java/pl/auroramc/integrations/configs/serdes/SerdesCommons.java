package pl.auroramc.integrations.configs.serdes;

import eu.okaeri.configs.serdes.SerdesRegistry;
import org.jetbrains.annotations.NotNull;
import pl.auroramc.integrations.configs.serdes.standard.SerdesStandard;

public class SerdesCommons extends eu.okaeri.configs.serdes.commons.SerdesCommons {

  @Override
  public void register(final @NotNull SerdesRegistry registry) {
    super.register(registry);
    registry.register(new SerdesStandard());
  }
}

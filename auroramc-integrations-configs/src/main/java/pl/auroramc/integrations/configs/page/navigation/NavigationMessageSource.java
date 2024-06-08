package pl.auroramc.integrations.configs.page.navigation;

import eu.okaeri.configs.OkaeriConfig;
import pl.auroramc.messages.message.MutableMessage;

public class NavigationMessageSource extends OkaeriConfig {

  public MutableMessage nameOfNextPageButton = MutableMessage.of("<gray>Następna strona");

  public MutableMessage nameOfPrevPageButton = MutableMessage.of("<gray>Poprzednia strona");

  public MutableMessage loreOfNextPageButton =
      MutableMessage.of("<gray>Naciśnij, aby przejść do następnej strony.");

  public MutableMessage loreOfPrevPageButton =
      MutableMessage.of("<gray>Naciśnij, aby przejść do poprzedniej strony.");
}

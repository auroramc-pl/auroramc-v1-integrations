package pl.auroramc.integrations.configs.page.navigation;

import eu.okaeri.configs.OkaeriConfig;
import pl.auroramc.messages.message.MutableMessage;

public class NavigationMessageSource extends OkaeriConfig {

  public MutableMessage nameOfNextPageButton = MutableMessage.of("<gradient:#c95e7b:#ed7d95:#ed7d95:#b55e7b>Następna strona");

  public MutableMessage nameOfPrevPageButton = MutableMessage.of("<gradient:#c95e7b:#ed7d95:#ed7d95:#b55e7b>Poprzednia strona");

  public MutableMessage loreOfNextPageButton =
      MutableMessage.of("<gradient:#f5c894:#f6d4a2:#f9e2b4:#f6d4a2:#f5c894>Naciśnij, aby przejść do następnej strony.");

  public MutableMessage loreOfPrevPageButton =
      MutableMessage.of("<gradient:#f5c894:#f6d4a2:#f9e2b4:#f6d4a2:#f5c894>Naciśnij, aby przejść do poprzedniej strony.");
}

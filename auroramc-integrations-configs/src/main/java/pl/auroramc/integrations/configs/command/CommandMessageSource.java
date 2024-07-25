package pl.auroramc.integrations.configs.command;

import eu.okaeri.configs.OkaeriConfig;
import pl.auroramc.messages.message.MutableMessage;

public class CommandMessageSource extends OkaeriConfig {

  public MutableMessage commandOnCooldown =
      MutableMessage.of(
          "<gradient:#b51c1c:#d33131:#c72929:#d33131:#b51c1c>Musisz odczekać jeszcze <gradient:#ffad33:#ff8052:#ffdb57:#ff8052:#ffdb57:#ffad33><duration><gradient:#b51c1c:#d33131:#c72929:#d33131:#b51c1c>, zanim ponownie użyjesz tej komendy.");

  public MutableMessage availableSchematicsSuggestion =
      MutableMessage.of("<gradient:#b51c1c:#d33131:#c72929:#d33131:#b51c1c>Poprawne użycie: <gradient:#ffad33:#ff8052:#ffdb57:#ff8052:#ffdb57:#ffad33><newline><schematics>");

  public MutableMessage executionOfCommandIsNotPermitted =
      MutableMessage.of("<gradient:#b51c1c:#d33131:#c72929:#d33131:#b51c1c>Nie posiadasz wystarczających uprawnień aby użyć tej komendy.");

  public MutableMessage executionFromConsoleIsUnsupported =
      MutableMessage.of("<gradient:#b51c1c:#d33131:#c72929:#d33131:#b51c1c>Nie możesz użyć tej konsoli z poziomu konsoli.");

  public MutableMessage specifiedPlayerIsUnknown =
      MutableMessage.of("<gradient:#b51c1c:#d33131:#c72929:#d33131:#b51c1c>Wskazany przez ciebie gracz nie istnieje, lub jest Offline.");
}
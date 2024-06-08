package pl.auroramc.integrations.configs.command;

import eu.okaeri.configs.OkaeriConfig;
import pl.auroramc.messages.message.MutableMessage;

public class CommandMessageSource extends OkaeriConfig {

  public MutableMessage commandOnCooldown =
      MutableMessage.of(
          "<red>Musisz odczekać jeszcze <yellow>{duration}<red>, zanim ponownie użyjesz tej komendy.");

  public MutableMessage availableSchematicsSuggestion =
      MutableMessage.of("<red>Poprawne użycie: <yellow><newline>{schematics}");

  public MutableMessage executionOfCommandIsNotPermitted =
      MutableMessage.of("<red>Nie posiadasz wystarczających uprawnień aby użyć tej komendy.");

  public MutableMessage executionFromConsoleIsUnsupported =
      MutableMessage.of("<red>Nie możesz użyć tej konsoli z poziomu konsoli.");

  public MutableMessage specifiedPlayerIsUnknown =
      MutableMessage.of("<red>Wskazany przez ciebie gracz nie istnieje, lub jest Offline.");
}
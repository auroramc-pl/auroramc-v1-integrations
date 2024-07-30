package pl.auroramc.integrations.configs.message;

import pl.auroramc.messages.i18n.MessageSource;

public class IntegrationsMessageSource extends MessageSource {

  public String configurationReloadSuccess =
      "<gradient:#c95e7b:#ed7d95:#ed7d95:#b55e7b>Konfiguracja oraz wiadomości wtyczki zostały przeładowane.";

  public String configurationReloadFailure =
      "<gradient:#b51c1c:#d33131:#c72929:#d33131:#b51c1c>Wystąpił błąd podczas przeładowywania konfiguracji oraz wiadomości wtyczki.";

  public String commandOnCooldown =
      "<gradient:#b51c1c:#d33131:#c72929:#d33131:#b51c1c>Musisz odczekać jeszcze <gradient:#ffad33:#ff8052:#ffdb57:#ff8052:#ffdb57:#ffad33><duration><gradient:#b51c1c:#d33131:#c72929:#d33131:#b51c1c>, zanim ponownie użyjesz tej komendy.";

  public String availableSchematicsSuggestion =
      "<gradient:#b51c1c:#d33131:#c72929:#d33131:#b51c1c>Poprawne użycie: <gradient:#ffad33:#ff8052:#ffdb57:#ff8052:#ffdb57:#ffad33><newline><schematics>";

  public String executionOfCommandIsNotPermitted =
      "<gradient:#b51c1c:#d33131:#c72929:#d33131:#b51c1c>Nie posiadasz wystarczających uprawnień aby użyć tej komendy.";

  public String executionFromConsoleIsUnsupported =
      "<gradient:#b51c1c:#d33131:#c72929:#d33131:#b51c1c>Nie możesz użyć tej konsoli z poziomu konsoli.";

  public String specifiedPlayerIsUnknown =
      "<gradient:#b51c1c:#d33131:#c72929:#d33131:#b51c1c>Wskazany przez ciebie gracz nie istnieje, lub jest Offline.";

  public String navigationNameOfNextPageButton =
      "<gradient:#c95e7b:#ed7d95:#ed7d95:#b55e7b>Następna strona";

  public String navigationLoreOfNextPageButton =
      "<gradient:#f5c894:#f6d4a2:#f9e2b4:#f6d4a2:#f5c894>Naciśnij, aby przejść do następnej strony.";

  public String navigationNameOfPrevPageButton =
      "<gradient:#c95e7b:#ed7d95:#ed7d95:#b55e7b>Poprzednia strona";

  public String navigationLoreOfPrevPageButton =
      "<gradient:#f5c894:#f6d4a2:#f9e2b4:#f6d4a2:#f5c894>Naciśnij, aby przejść do poprzedniej strony.";
}

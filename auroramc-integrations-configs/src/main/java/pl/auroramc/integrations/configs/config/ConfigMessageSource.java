package pl.auroramc.integrations.configs.config;

import eu.okaeri.configs.OkaeriConfig;
import pl.auroramc.messages.message.MutableMessage;

public class ConfigMessageSource extends OkaeriConfig {

  public MutableMessage configurationReloadSuccess =
      MutableMessage.of(
          "<gradient:#c95e7b:#ed7d95:#ed7d95:#b55e7b>Konfiguracja oraz wiadomości wtyczki zostały przeładowane.");

  public MutableMessage configurationReloadFailure =
      MutableMessage.of(
          "<gradient:#b51c1c:#d33131:#c72929:#d33131:#b51c1c>Wystąpił błąd podczas przeładowywania konfiguracji oraz wiadomości wtyczki.");
}

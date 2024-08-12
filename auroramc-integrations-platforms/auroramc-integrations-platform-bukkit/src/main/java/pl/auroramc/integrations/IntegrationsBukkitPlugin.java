package pl.auroramc.integrations;

import static pl.auroramc.commons.bukkit.scheduler.BukkitSchedulerFactory.getBukkitScheduler;
import static pl.auroramc.messages.i18n.MessageSourceUtils.getMessageSourceTemplate;
import static pl.auroramc.messages.message.compiler.BukkitMessageCompiler.getBukkitMessageCompiler;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import java.util.Optional;
import org.bukkit.plugin.java.JavaPlugin;
import pl.auroramc.commons.scheduler.Scheduler;
import pl.auroramc.integrations.configs.ConfigFactory;
import pl.auroramc.integrations.configs.message.IntegrationsMessageSource;
import pl.auroramc.messages.i18n.BukkitMessageFacade;
import pl.auroramc.messages.i18n.MessageSource;
import pl.auroramc.messages.message.compiler.BukkitMessageCompiler;

public abstract class IntegrationsBukkitPlugin extends JavaPlugin {

  private static final String I18N_BUNDLE_DIRECTORY_NAME = "i18n";
  private static final String I18N_BUNDLE_FILE_EXTENSION = ".yml";
  private static final String INTEGRATIONS_BUNDLE_NAME = "integrations_";
  private ConfigFactory configFactory;
  private Scheduler scheduler;
  private BukkitMessageCompiler messageCompiler;
  private IntegrationsMessageSource integrationsMessageSource;

  @Override
  public void onEnable() {
    configFactory = new ConfigFactory(getDataFolder().toPath(), YamlBukkitConfigurer::new);

    scheduler = getBukkitScheduler(this);

    messageCompiler = getBukkitMessageCompiler(scheduler);

    onStartup();
  }

  public abstract void onStartup();

  protected <T extends OkaeriConfig> T produceConfig(
      final Class<T> configType,
      final String configFileName,
      final OkaeriSerdesPack... serdesPacks) {
    return configFactory.produceConfig(configType, configFileName, serdesPacks);
  }

  protected <T extends MessageSource> T registerMessageSource(
      final BukkitMessageFacade messageFacade,
      final Class<T> messageSourceType,
      final String bundleName) {
    messageFacade.registerMessageSource(
        messageSourceType,
        getFile(),
        getDataFolder(),
        I18N_BUNDLE_DIRECTORY_NAME,
        bundleName,
        I18N_BUNDLE_FILE_EXTENSION);
    return getMessageSourceTemplate(messageSourceType);
  }

  protected IntegrationsMessageSource registerIntegrationsMessageSource(
      final BukkitMessageFacade messageFacade) {
    return Optional.ofNullable(integrationsMessageSource)
        .orElseGet(() -> registerIntegrationsMessageSource0(messageFacade));
  }

  private IntegrationsMessageSource registerIntegrationsMessageSource0(
      final BukkitMessageFacade messageFacade) {
    final IntegrationsMessageSource messageSource =
        registerMessageSource(
            messageFacade, IntegrationsMessageSource.class, INTEGRATIONS_BUNDLE_NAME);
    this.integrationsMessageSource = messageSource;
    return messageSource;
  }

  public Scheduler getScheduler() {
    return scheduler;
  }

  public BukkitMessageCompiler getMessageCompiler() {
    return messageCompiler;
  }

  public IntegrationsMessageSource getIntegrationsMessageSource() {
    return integrationsMessageSource;
  }
}

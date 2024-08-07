package pl.auroramc.integrations;

import static java.util.Locale.ENGLISH;
import static pl.auroramc.commons.bukkit.scheduler.BukkitSchedulerFactory.getBukkitScheduler;
import static pl.auroramc.messages.i18n.BukkitMessageFacade.getBukkitMessageFacade;
import static pl.auroramc.messages.i18n.MessageSourceUtils.getMessageSourceTemplate;
import static pl.auroramc.messages.message.compiler.BukkitMessageCompiler.getBukkitMessageCompiler;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
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
  private BukkitMessageFacade messageFacade;
  private BukkitMessageCompiler messageCompiler;
  private IntegrationsMessageSource integrationsMessageSource;

  @Override
  public void onEnable() {
    configFactory = new ConfigFactory(getDataFolder().toPath(), YamlBukkitConfigurer::new);

    scheduler = getBukkitScheduler(this);

    messageFacade = getBukkitMessageFacade(YamlBukkitConfigurer::new, ENGLISH);
    messageCompiler = getBukkitMessageCompiler(scheduler);

    integrationsMessageSource =
        registerMessageSource(IntegrationsMessageSource.class, INTEGRATIONS_BUNDLE_NAME);

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
      final Class<T> messageSourceType, final String bundleName) {
    messageFacade.registerMessageSource(
        messageSourceType,
        getFile(),
        getDataFolder(),
        I18N_BUNDLE_DIRECTORY_NAME,
        bundleName,
        I18N_BUNDLE_FILE_EXTENSION);
    return getMessageSourceTemplate(messageSourceType);
  }

  public Scheduler getScheduler() {
    return scheduler;
  }

  public BukkitMessageFacade getMessageFacade() {
    return messageFacade;
  }

  public BukkitMessageCompiler getMessageCompiler() {
    return messageCompiler;
  }

  public IntegrationsMessageSource getIntegrationsMessageSource() {
    return integrationsMessageSource;
  }
}

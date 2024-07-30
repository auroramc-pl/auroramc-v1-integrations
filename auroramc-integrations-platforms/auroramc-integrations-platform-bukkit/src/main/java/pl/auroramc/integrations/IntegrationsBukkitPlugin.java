package pl.auroramc.integrations;

import static java.util.Locale.ENGLISH;
import static pl.auroramc.commons.bukkit.scheduler.BukkitSchedulerFactory.getBukkitScheduler;
import static pl.auroramc.messages.i18n.BukkitMessageFacade.getBukkitMessageFacade;
import static pl.auroramc.messages.i18n.MessageSourceUtils.getMessageSourceTemplate;
import static pl.auroramc.messages.message.compiler.BukkitMessageCompiler.getBukkitMessageCompiler;
import static pl.auroramc.messages.viewer.BukkitViewerFacade.getBukkitViewerFacade;

import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import org.bukkit.plugin.java.JavaPlugin;
import pl.auroramc.commons.scheduler.Scheduler;
import pl.auroramc.integrations.configs.message.IntegrationsMessageSource;
import pl.auroramc.messages.i18n.BukkitMessageFacade;
import pl.auroramc.messages.i18n.MessageSource;
import pl.auroramc.messages.message.compiler.BukkitMessageCompiler;
import pl.auroramc.messages.viewer.BukkitViewerFacade;

public abstract class IntegrationsBukkitPlugin extends JavaPlugin {

  private static final String I18N_BUNDLE_DIRECTORY_NAME = "i18n";
  private static final String I18N_BUNDLE_FILE_EXTENSION = ".yml";
  private Scheduler scheduler;
  private BukkitMessageFacade messageFacade;
  private BukkitMessageCompiler messageCompiler;
  private BukkitViewerFacade viewerFacade;
  private IntegrationsMessageSource integrationsMessageSource;

  @Override
  public void onEnable() {
    scheduler = getBukkitScheduler(this);

    messageFacade = getBukkitMessageFacade(YamlBukkitConfigurer::new, ENGLISH);
    messageCompiler = getBukkitMessageCompiler(scheduler);
    viewerFacade = getBukkitViewerFacade(getServer());

    integrationsMessageSource =
        registerMessageSource(IntegrationsMessageSource.class, "integrations_");

    onStartup();
  }

  public abstract void onStartup();

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

  public BukkitViewerFacade getViewerFacade() {
    return viewerFacade;
  }

  public IntegrationsMessageSource getIntegrationsMessageSource() {
    return integrationsMessageSource;
  }
}

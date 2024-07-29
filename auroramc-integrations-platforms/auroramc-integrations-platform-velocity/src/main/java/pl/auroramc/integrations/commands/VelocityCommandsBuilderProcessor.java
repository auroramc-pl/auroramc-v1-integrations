package pl.auroramc.integrations.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.LiteCommandsInternal;
import dev.rollczi.litecommands.permission.MissingPermissions;
import dev.rollczi.litecommands.velocity.LiteVelocitySettings;
import dev.rollczi.litecommands.velocity.tools.VelocityOnlyPlayerContextual;
import pl.auroramc.integrations.commands.argument.resolver.PlayerArgumentResolver;
import pl.auroramc.integrations.commands.permission.DefaultMissingPermissionsHandler;
import pl.auroramc.integrations.configs.command.CommandMessageSource;
import pl.auroramc.messages.i18n.VelocityMessageFacade;
import pl.auroramc.messages.message.compiler.VelocityMessageCompiler;
import pl.auroramc.messages.viewer.VelocityViewer;
import pl.auroramc.messages.viewer.VelocityViewerFacade;

public class VelocityCommandsBuilderProcessor
    extends CommandsBuilderProcessor<CommandSource, VelocityViewer, LiteVelocitySettings> {

  private final ProxyServer server;
  private final CommandMessageSource messageSource;
  private final VelocityMessageCompiler messageCompiler;
  private final VelocityViewerFacade viewerFacade;

  public VelocityCommandsBuilderProcessor(
      final ProxyServer server,
      final CommandMessageSource messageSource,
      final VelocityMessageFacade messageFacade,
      final VelocityMessageCompiler messageCompiler,
      final VelocityViewerFacade viewerFacade) {
    super(messageSource, messageFacade, messageCompiler, viewerFacade);
    this.server = server;
    this.messageSource = messageSource;
    this.messageCompiler = messageCompiler;
    this.viewerFacade = viewerFacade;
  }

  @Override
  public void process(
      final LiteCommandsBuilder<CommandSource, LiteVelocitySettings, ?> builder,
      final LiteCommandsInternal<CommandSource, LiteVelocitySettings> internal) {
    super.process(builder, internal);
    builder
        .context(
            Player.class,
            new VelocityOnlyPlayerContextual<>(messageSource.executionFromConsoleIsUnsupported))
        .result(
            MissingPermissions.class,
            new DefaultMissingPermissionsHandler<>(messageSource, messageCompiler, viewerFacade))
        .argument(Player.class, new PlayerArgumentResolver<>(server, messageSource));
  }
}

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
import pl.auroramc.messages.i18n.MessageFacade;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.message.compiler.VelocityMessageCompiler;
import pl.auroramc.messages.viewer.VelocityViewer;
import pl.auroramc.messages.viewer.ViewerFacade;

public class VelocityCommandsBuilderProcessor
    extends CommandsBuilderProcessor<CommandSource, VelocityViewer, LiteVelocitySettings> {

  private final ProxyServer server;
  private final CommandMessageSource messageSource;
  private final VelocityMessageCompiler messageCompiler;

  public VelocityCommandsBuilderProcessor(
      final ProxyServer server,
      final CommandMessageSource messageSource,
      final MessageFacade<MutableMessage> messageFacade,
      final VelocityMessageCompiler messageCompiler,
      final ViewerFacade<VelocityViewer> viewerFacade) {
    super(messageSource, messageFacade, messageCompiler, viewerFacade);
    this.server = server;
    this.messageSource = messageSource;
    this.messageCompiler = messageCompiler;
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
            new DefaultMissingPermissionsHandler<>(messageSource, messageCompiler))
        .argument(Player.class, new PlayerArgumentResolver<>(server, messageSource));
  }
}

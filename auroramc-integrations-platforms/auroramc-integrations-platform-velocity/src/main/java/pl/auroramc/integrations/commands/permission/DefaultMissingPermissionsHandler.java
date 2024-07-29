package pl.auroramc.integrations.commands.permission;

import static pl.auroramc.integrations.commands.CommandsUtils.getViewer;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.permission.MissingPermissions;
import dev.rollczi.litecommands.permission.MissingPermissionsHandler;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.integrations.configs.command.CommandMessageSource;
import pl.auroramc.messages.message.compiler.VelocityMessageCompiler;
import pl.auroramc.messages.viewer.VelocityViewerFacade;
import pl.auroramc.messages.viewer.Viewer;

public class DefaultMissingPermissionsHandler<T extends Audience>
    implements MissingPermissionsHandler<T> {

  private final CommandMessageSource messageSource;
  private final VelocityMessageCompiler messageCompiler;
  private final VelocityViewerFacade viewerFacade;

  public DefaultMissingPermissionsHandler(
      final CommandMessageSource messageSource,
      final VelocityMessageCompiler messageCompiler,
      final VelocityViewerFacade viewerFacade) {
    this.messageSource = messageSource;
    this.messageCompiler = messageCompiler;
    this.viewerFacade = viewerFacade;
  }

  @Override
  public void handle(
      final Invocation<T> invocation,
      final MissingPermissions context,
      final ResultHandlerChain<T> chain) {
    final Viewer viewer = getViewer(viewerFacade, invocation);
    messageCompiler.compile(messageSource.executionOfCommandIsNotPermitted).deliver(viewer);
  }
}

package pl.auroramc.integrations.commands.handler.result.message;

import static pl.auroramc.integrations.commands.CommandsUtils.getViewer;

import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.message.compiler.CompiledMessage;
import pl.auroramc.messages.viewer.Viewer;
import pl.auroramc.messages.viewer.ViewerFacade;

public class CompiledMessageHandler<SENDER extends Audience, VIEWER extends Viewer>
    implements ResultHandler<SENDER, CompiledMessage> {

  private final ViewerFacade<VIEWER> viewerFacade;

  public CompiledMessageHandler(final ViewerFacade<VIEWER> viewerFacade) {
    this.viewerFacade = viewerFacade;
  }

  @Override
  public void handle(
      final Invocation<SENDER> invocation,
      final CompiledMessage message,
      final ResultHandlerChain<SENDER> chain) {
    final Viewer viewer = getViewer(viewerFacade, invocation);
    message.deliver(viewer);
  }
}

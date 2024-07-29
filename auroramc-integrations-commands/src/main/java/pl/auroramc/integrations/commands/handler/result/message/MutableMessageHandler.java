package pl.auroramc.integrations.commands.handler.result.message;

import static pl.auroramc.integrations.commands.CommandsUtils.getViewer;

import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.message.compiler.MessageCompiler;
import pl.auroramc.messages.viewer.Viewer;
import pl.auroramc.messages.viewer.ViewerFacade;

public class MutableMessageHandler<SENDER extends Audience, VIEWER extends Viewer>
    implements ResultHandler<SENDER, MutableMessage> {

  private final MessageCompiler messageCompiler;
  private final ViewerFacade<VIEWER> viewerFacade;

  public MutableMessageHandler(
      final MessageCompiler messageCompiler, final ViewerFacade<VIEWER> viewerFacade) {
    this.messageCompiler = messageCompiler;
    this.viewerFacade = viewerFacade;
  }

  @Override
  public void handle(
      final Invocation<SENDER> invocation,
      final MutableMessage message,
      final ResultHandlerChain<SENDER> chain) {
    final Viewer viewer = getViewer(viewerFacade, invocation);
    messageCompiler.compile(message).deliver(viewer);
  }
}

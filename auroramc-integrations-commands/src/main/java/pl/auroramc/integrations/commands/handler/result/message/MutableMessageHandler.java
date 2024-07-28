package pl.auroramc.integrations.commands.handler.result.message;

import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.message.compiler.MessageCompiler;
import pl.auroramc.messages.viewer.Viewer;

public class MutableMessageHandler<SENDER extends Audience, VIEWER extends Viewer>
    implements ResultHandler<SENDER, MutableMessage> {

  private final MessageCompiler<VIEWER> messageCompiler;

  public MutableMessageHandler(final MessageCompiler<VIEWER> messageCompiler) {
    this.messageCompiler = messageCompiler;
  }

  @Override
  public void handle(
      final Invocation<SENDER> invocation,
      final MutableMessage message,
      final ResultHandlerChain<SENDER> chain) {
    messageCompiler.compile(message).deliver(invocation.sender());
  }
}

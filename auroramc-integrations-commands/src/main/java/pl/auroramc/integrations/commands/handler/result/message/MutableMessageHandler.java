package pl.auroramc.integrations.commands.handler.result.message;

import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.message.compiler.MessageCompiler;

public class MutableMessageHandler<T extends Audience> implements ResultHandler<T, MutableMessage> {

  private final MessageCompiler<T> messageCompiler;

  public MutableMessageHandler(final MessageCompiler<T> messageCompiler) {
    this.messageCompiler = messageCompiler;
  }

  @Override
  public void handle(
      final Invocation<T> invocation,
      final MutableMessage message,
      final ResultHandlerChain<T> chain) {
    messageCompiler.compile(message).deliver(invocation.sender());
  }
}
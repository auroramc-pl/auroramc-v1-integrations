package pl.auroramc.integrations.commands.handler.result.message;

import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.message.compiler.CompiledMessage;

public class CompiledMessageHandler<T extends Audience> implements ResultHandler<T, CompiledMessage> {

  @Override
  public void handle(
      final Invocation<T> invocation,
      final CompiledMessage message,
      final ResultHandlerChain<T> chain) {
    message.deliver(invocation.sender());
  }
}
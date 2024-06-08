package pl.auroramc.integrations.commands.handler.result.message;

import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.message.compiler.MessageCompiler;
import pl.auroramc.messages.message.group.MutableMessageGroup;

public class MutableMessageGroupHandler<T extends Audience>
    implements ResultHandler<T, MutableMessageGroup> {

  private final MessageCompiler<T> messageCompiler;

  public MutableMessageGroupHandler(final MessageCompiler<T> messageCompiler) {
    this.messageCompiler = messageCompiler;
  }

  @Override
  public void handle(
      final Invocation<T> invocation,
      final MutableMessageGroup group,
      final ResultHandlerChain<T> chain) {
    messageCompiler.compileGroup(group).deliver();
  }
}

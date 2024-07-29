package pl.auroramc.integrations.commands.handler.result.message;

import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.message.compiler.MessageCompiler;
import pl.auroramc.messages.message.group.MutableMessageGroup;

public class MutableMessageGroupHandler<SENDER extends Audience>
    implements ResultHandler<SENDER, MutableMessageGroup> {

  private final MessageCompiler messageCompiler;

  public MutableMessageGroupHandler(final MessageCompiler messageCompiler) {
    this.messageCompiler = messageCompiler;
  }

  @Override
  public void handle(
      final Invocation<SENDER> invocation,
      final MutableMessageGroup group,
      final ResultHandlerChain<SENDER> chain) {
    messageCompiler.compileGroup(group).deliver();
  }
}

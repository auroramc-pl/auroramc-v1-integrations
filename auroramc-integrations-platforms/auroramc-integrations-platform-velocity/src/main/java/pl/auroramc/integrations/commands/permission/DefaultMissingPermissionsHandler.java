package pl.auroramc.integrations.commands.permission;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.permission.MissingPermissions;
import dev.rollczi.litecommands.permission.MissingPermissionsHandler;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.integrations.configs.command.CommandMessageSource;
import pl.auroramc.messages.message.compiler.VelocityMessageCompiler;

public class DefaultMissingPermissionsHandler<T extends Audience>
    implements MissingPermissionsHandler<T> {

  private final CommandMessageSource messageSource;
  private final VelocityMessageCompiler messageCompiler;

  public DefaultMissingPermissionsHandler(
      final CommandMessageSource messageSource, final VelocityMessageCompiler messageCompiler) {
    this.messageSource = messageSource;
    this.messageCompiler = messageCompiler;
  }

  @Override
  public void handle(
      final Invocation<T> invocation,
      final MissingPermissions context,
      final ResultHandlerChain<T> chain) {
    messageCompiler
        .compile(messageSource.executionOfCommandIsNotPermitted)
        .deliver(invocation.sender());
  }
}

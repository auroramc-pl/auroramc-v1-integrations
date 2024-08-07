package pl.auroramc.integrations.commands.permission;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.permission.MissingPermissions;
import dev.rollczi.litecommands.permission.MissingPermissionsHandler;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.i18n.Message;

public class DefaultMissingPermissionsHandler<T extends Audience>
    implements MissingPermissionsHandler<T> {

  private final Message executionOfCommandIsNotPermitted;

  public DefaultMissingPermissionsHandler(final Message executionOfCommandIsNotPermitted) {
    this.executionOfCommandIsNotPermitted = executionOfCommandIsNotPermitted;
  }

  @Override
  public void handle(
      final Invocation<T> invocation,
      final MissingPermissions context,
      final ResultHandlerChain<T> chain) {
    chain.resolve(invocation, executionOfCommandIsNotPermitted);
  }
}

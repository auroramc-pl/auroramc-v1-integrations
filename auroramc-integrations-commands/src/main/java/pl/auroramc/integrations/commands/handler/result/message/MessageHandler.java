package pl.auroramc.integrations.commands.handler.result.message;

import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.i18n.Message;
import pl.auroramc.messages.i18n.MessageFacade;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.message.compiler.MessageCompiler;

public class MessageHandler<T extends Audience> implements ResultHandler<T, Message> {

  private final MessageFacade<MutableMessage, T> messageFacade;
  private final MessageCompiler<T> messageCompiler;

  public MessageHandler(
      final MessageFacade<MutableMessage, T> messageFacade,
      final MessageCompiler<T> messageCompiler) {
    this.messageFacade = messageFacade;
    this.messageCompiler = messageCompiler;
  }

  @Override
  public void handle(
      final Invocation<T> invocation, final Message message, final ResultHandlerChain<T> chain) {
    messageCompiler
        .compile(
            messageFacade
                .getMessage(invocation.sender(), message.key())
                .placeholders(message.placeholders()))
        .deliver(invocation.sender());
  }
}
package pl.auroramc.integrations.commands.argument.resolver.standard;

import static dev.rollczi.litecommands.argument.parser.ParseResult.failure;
import static dev.rollczi.litecommands.argument.parser.ParseResult.success;
import static dev.rollczi.litecommands.message.LiteMessages.INVALID_NUMBER;
import static java.math.RoundingMode.HALF_UP;
import static pl.auroramc.commons.format.decimal.DecimalParser.getParsedDecimal;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.message.MessageRegistry;
import java.math.BigDecimal;

public class BigDecimalArgumentResolver<T>
    extends dev.rollczi.litecommands.argument.resolver.standard.BigDecimalArgumentResolver<T> {

  private final MessageRegistry<T> messageRegistry;

  public BigDecimalArgumentResolver(final MessageRegistry<T> messageRegistry) {
    super(messageRegistry);
    this.messageRegistry = messageRegistry;
  }

  @Override
  protected ParseResult<BigDecimal> parse(
      final Invocation<T> invocation, final Argument<BigDecimal> context, final String argument) {
    try {
      return success(getParsedDecimal(argument).setScale(2, HALF_UP));
    } catch (final NumberFormatException exception) {
      return failure(messageRegistry.getInvoked(INVALID_NUMBER, invocation, argument));
    }
  }
}
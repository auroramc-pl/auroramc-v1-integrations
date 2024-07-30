package pl.auroramc.integrations.commands.argument.resolver;

import static dev.rollczi.litecommands.argument.parser.ParseResult.failure;
import static java.time.Duration.ofSeconds;
import static pl.auroramc.commons.memoize.MemoizedSupplier.memoize;
import static pl.auroramc.messages.i18n.Message.message;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import java.util.function.Supplier;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.integrations.configs.message.IntegrationsMessageSource;

public class PlayerArgumentResolver<T extends Audience> extends ArgumentResolver<T, Player> {

  private final ProxyServer server;
  private final IntegrationsMessageSource messageSource;
  private final Supplier<SuggestionResult> memoizedPlayers;

  public PlayerArgumentResolver(
      final ProxyServer server, final IntegrationsMessageSource messageSource) {
    this.server = server;
    this.messageSource = messageSource;
    this.memoizedPlayers = memoize(ofSeconds(5), this::getPlayerSuggestions);
  }

  @Override
  protected ParseResult<Player> parse(
      final Invocation<T> invocation, final Argument<Player> context, final String argument) {
    return server
        .getPlayer(argument)
        .map(ParseResult::success)
        .orElseGet(() -> failure(message(messageSource.specifiedPlayerIsUnknown)));
  }

  @Override
  public SuggestionResult suggest(
      final Invocation<T> invocation,
      final Argument<Player> argument,
      final SuggestionContext context) {
    return memoizedPlayers.get();
  }

  private SuggestionResult getPlayerSuggestions() {
    return server.getAllPlayers().stream()
        .map(Player::getUsername)
        .collect(SuggestionResult.collector());
  }
}

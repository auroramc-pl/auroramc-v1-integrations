package pl.auroramc.integrations.commands;

import static dev.rollczi.litecommands.bukkit.LiteBukkitMessages.PLAYER_NOT_FOUND;
import static dev.rollczi.litecommands.bukkit.LiteBukkitMessages.PLAYER_ONLY;

import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.LiteCommandsInternal;
import dev.rollczi.litecommands.bukkit.LiteBukkitSettings;
import org.bukkit.command.CommandSender;
import pl.auroramc.integrations.configs.command.CommandMessageSource;
import pl.auroramc.messages.i18n.BukkitMessageFacade;
import pl.auroramc.messages.message.compiler.BukkitMessageCompiler;
import pl.auroramc.messages.viewer.BukkitViewer;
import pl.auroramc.messages.viewer.BukkitViewerFacade;

public class BukkitCommandsBuilderProcessor
    extends CommandsBuilderProcessor<CommandSender, BukkitViewer, LiteBukkitSettings> {

  private final CommandMessageSource messageSource;

  public BukkitCommandsBuilderProcessor(
      final CommandMessageSource messageSource,
      final BukkitMessageFacade messageFacade,
      final BukkitMessageCompiler messageCompiler,
      final BukkitViewerFacade viewerFacade) {
    super(messageSource, messageFacade, messageCompiler, viewerFacade);
    this.messageSource = messageSource;
  }

  @Override
  public void process(
      final LiteCommandsBuilder<CommandSender, LiteBukkitSettings, ?> builder,
      final LiteCommandsInternal<CommandSender, LiteBukkitSettings> internal) {
    super.process(builder, internal);
    builder
        .message(PLAYER_ONLY, messageSource.executionFromConsoleIsUnsupported)
        .message(PLAYER_NOT_FOUND, messageSource.specifiedPlayerIsUnknown);
  }
}

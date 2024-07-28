package pl.auroramc.integrations.commands;

import static dev.rollczi.litecommands.message.LiteMessages.COMMAND_COOLDOWN;
import static dev.rollczi.litecommands.message.LiteMessages.INVALID_USAGE;
import static dev.rollczi.litecommands.message.LiteMessages.MISSING_PERMISSIONS;
import static dev.rollczi.litecommands.schematic.SchematicFormat.angleBrackets;
import static pl.auroramc.integrations.configs.command.CommandMessageSourcePaths.DURATION_PATH;
import static pl.auroramc.integrations.configs.command.CommandMessageSourcePaths.SCHEMATICS_PATH;
import static pl.auroramc.messages.message.MutableMessage.LINE_DELIMITER;

import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.LiteCommandsInternal;
import dev.rollczi.litecommands.platform.PlatformSettings;
import dev.rollczi.litecommands.processor.LiteBuilderProcessor;
import java.math.BigDecimal;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.integrations.commands.argument.resolver.standard.BigDecimalArgumentResolver;
import pl.auroramc.integrations.commands.handler.result.message.CompiledMessageHandler;
import pl.auroramc.integrations.commands.handler.result.message.MessageHandler;
import pl.auroramc.integrations.commands.handler.result.message.MutableMessageGroupHandler;
import pl.auroramc.integrations.commands.handler.result.message.MutableMessageHandler;
import pl.auroramc.integrations.commands.schematic.DefaultSchematicGenerator;
import pl.auroramc.integrations.configs.command.CommandMessageSource;
import pl.auroramc.messages.i18n.Message;
import pl.auroramc.messages.i18n.MessageFacade;
import pl.auroramc.messages.message.MutableMessage;
import pl.auroramc.messages.message.compiler.CompiledMessage;
import pl.auroramc.messages.message.compiler.MessageCompiler;
import pl.auroramc.messages.message.group.MutableMessageGroup;
import pl.auroramc.messages.viewer.Viewer;
import pl.auroramc.messages.viewer.ViewerFacade;

public class CommandsBuilderProcessor<
        SENDER extends Audience, VIEWER extends Viewer, SETTINGS extends PlatformSettings>
    implements LiteBuilderProcessor<SENDER, SETTINGS> {

  private final CommandMessageSource messageSource;
  private final MessageFacade<MutableMessage> messageFacade;
  private final MessageCompiler<VIEWER> messageCompiler;
  private final ViewerFacade<VIEWER> viewerFacade;

  public CommandsBuilderProcessor(
      final CommandMessageSource messageSource,
      final MessageFacade<MutableMessage> messageFacade,
      final MessageCompiler<VIEWER> messageCompiler,
      final ViewerFacade<VIEWER> viewerFacade) {
    this.messageSource = messageSource;
    this.messageFacade = messageFacade;
    this.messageCompiler = messageCompiler;
    this.viewerFacade = viewerFacade;
  }

  @Override
  public void process(
      final LiteCommandsBuilder<SENDER, SETTINGS, ?> builder,
      final LiteCommandsInternal<SENDER, SETTINGS> internal) {
    builder
        .argument(BigDecimal.class, new BigDecimalArgumentResolver<>(internal.getMessageRegistry()))
        .message(
            INVALID_USAGE,
            context ->
                messageSource.availableSchematicsSuggestion.placeholder(
                    SCHEMATICS_PATH, context.getSchematic().join(LINE_DELIMITER)))
        .message(MISSING_PERMISSIONS, messageSource.executionOfCommandIsNotPermitted)
        .message(
            COMMAND_COOLDOWN,
            context ->
                messageSource.commandOnCooldown.placeholder(
                    DURATION_PATH, context.getRemainingDuration()))
        .schematicGenerator(
            new DefaultSchematicGenerator<>(
                angleBrackets(), internal.getValidatorService(), internal.getWrapperRegistry()))
        .result(Message.class, new MessageHandler<>(messageFacade, messageCompiler, viewerFacade))
        .result(CompiledMessage.class, new CompiledMessageHandler<>())
        .result(MutableMessage.class, new MutableMessageHandler<>(messageCompiler))
        .result(MutableMessageGroup.class, new MutableMessageGroupHandler<>(messageCompiler));
  }
}

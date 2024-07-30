package pl.auroramc.integrations.commands;

import static dev.rollczi.litecommands.message.LiteMessages.COMMAND_COOLDOWN;
import static dev.rollczi.litecommands.message.LiteMessages.INVALID_USAGE;
import static dev.rollczi.litecommands.message.LiteMessages.MISSING_PERMISSIONS;
import static dev.rollczi.litecommands.schematic.SchematicFormat.angleBrackets;
import static net.kyori.adventure.text.Component.empty;
import static pl.auroramc.integrations.configs.message.IntegrationsMessageSourcePaths.DURATION_PATH;
import static pl.auroramc.integrations.configs.message.IntegrationsMessageSourcePaths.SCHEMATICS_PATH;
import static pl.auroramc.messages.i18n.Message.message;

import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.LiteCommandsInternal;
import dev.rollczi.litecommands.platform.PlatformSettings;
import dev.rollczi.litecommands.processor.LiteBuilderProcessor;
import dev.rollczi.litecommands.schematic.Schematic;
import java.math.BigDecimal;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import pl.auroramc.commons.component.ComponentReducer;
import pl.auroramc.integrations.commands.argument.resolver.standard.BigDecimalArgumentResolver;
import pl.auroramc.integrations.commands.handler.result.message.CompiledMessageHandler;
import pl.auroramc.integrations.commands.handler.result.message.MessageHandler;
import pl.auroramc.integrations.commands.handler.result.message.MutableMessageGroupHandler;
import pl.auroramc.integrations.commands.handler.result.message.MutableMessageHandler;
import pl.auroramc.integrations.commands.schematic.DefaultSchematicGenerator;
import pl.auroramc.integrations.configs.message.IntegrationsMessageSource;
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

  private final IntegrationsMessageSource messageSource;
  private final MessageFacade<MutableMessage> messageFacade;
  private final MessageCompiler messageCompiler;
  private final ViewerFacade<VIEWER> viewerFacade;

  public CommandsBuilderProcessor(
      final IntegrationsMessageSource messageSource,
      final MessageFacade<MutableMessage> messageFacade,
      final MessageCompiler messageCompiler,
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
                message(messageSource.availableSchematicsSuggestion)
                    .placeholder(SCHEMATICS_PATH, getMergedSchematics(context.getSchematic())))
        .message(MISSING_PERMISSIONS, message(messageSource.executionOfCommandIsNotPermitted))
        .message(
            COMMAND_COOLDOWN,
            context ->
                message(messageSource.commandOnCooldown)
                    .placeholder(DURATION_PATH, context.getRemainingDuration()))
        .schematicGenerator(
            new DefaultSchematicGenerator<>(
                angleBrackets(), internal.getValidatorService(), internal.getWrapperRegistry()))
        .result(Message.class, new MessageHandler<>(messageFacade, messageCompiler, viewerFacade))
        .result(CompiledMessage.class, new CompiledMessageHandler<>(viewerFacade))
        .result(MutableMessage.class, new MutableMessageHandler<>(messageCompiler, viewerFacade))
        .result(MutableMessageGroup.class, new MutableMessageGroupHandler<>(messageCompiler));
  }

  private Component getMergedSchematics(final Schematic schematic) {
    return schematic.all().stream()
        .map(Component::text)
        .map(Component.class::cast)
        .reduce(empty(), new ComponentReducer());
  }
}

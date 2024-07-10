package pl.auroramc.integrations.commands.schematic;

import static dev.rollczi.litecommands.meta.Meta.DESCRIPTION;
import static java.lang.String.join;
import static java.util.stream.Collectors.joining;
import static pl.auroramc.commons.format.StringUtils.BLANK;

import dev.rollczi.litecommands.command.executor.CommandExecutor;
import dev.rollczi.litecommands.schematic.SchematicFormat;
import dev.rollczi.litecommands.schematic.SchematicInput;
import dev.rollczi.litecommands.schematic.SimpleSchematicGenerator;
import dev.rollczi.litecommands.validator.ValidatorService;
import dev.rollczi.litecommands.wrapper.WrapperRegistry;
import java.util.List;

public class DefaultSchematicGenerator<T> extends SimpleSchematicGenerator<T> {

  private static final String SEPARATOR = " ";
  private static final String DESCRIPTION_FORMAT = " - %s";

  public DefaultSchematicGenerator(
      final SchematicFormat format,
      final ValidatorService<T> validatorService,
      final WrapperRegistry wrapperRegistry) {
    super(format, validatorService, wrapperRegistry);
  }

  @Override
  protected String generateExecutor(
      final SchematicInput<T> input, final CommandExecutor<T> executor) {
    final String original =
        executor.getArguments().stream()
            .map(
                argument ->
                    generateArgumentFormat(input, argument)
                        .formatted(generateArgumentName(input, argument)))
            .collect(joining(SEPARATOR));
    final String description = generateDescriptionMeta(executor);
    final String appendix =
        description.isEmpty() ? BLANK : DESCRIPTION_FORMAT.formatted(description);
    return original + appendix;
  }

  protected String generateDescriptionMeta(final CommandExecutor<T> executor) {
    final List<List<String>> descriptions = executor.metaCollector().collect(DESCRIPTION);
    if (descriptions.isEmpty()) {
      return BLANK;
    }
    return join(SEPARATOR, descriptions.getFirst());
  }
}

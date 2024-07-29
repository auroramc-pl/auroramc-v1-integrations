package pl.auroramc.integrations.kyori;

import static java.util.Arrays.stream;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.format.NamedTextColor.DARK_RED;
import static net.kyori.adventure.text.format.TextDecoration.State.NOT_SET;
import static org.apache.commons.lang3.StringUtils.countMatches;

import java.util.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;

/**
 * Source: {@link <a href="https://gist.github.com/Minikloon/e6a7679d171b90dc4e0731db46d77c84">Kyori
 * Adventure Component Word Wrapping</a>} Author: {@link <a
 * href="https://github.com/Minikloon">Minikloon</a>}
 */
public final class ComponentWrapper {

  private ComponentWrapper() {}

  public static List<Component> wrap(final Component component, final int length) {
    if (!(component instanceof TextComponent text)) {
      return List.of(component);
    }

    final List<Component> wrapped = new ArrayList<>();
    final List<TextComponent> parts = flatten(text);

    Component currentLine = empty();
    int lineLength = 0;
    for (int i = 0; i < parts.size(); i++) {
      final TextComponent part = parts.get(i);
      final Style style = part.style();
      final String content = part.content();

      final TextComponent nextPart = i == parts.size() - 1 ? null : parts.get(i + 1);
      final boolean join =
          nextPart != null && (part.content().endsWith(" ") || nextPart.content().startsWith(" "));

      StringBuilder lineBuilder = new StringBuilder();

      String[] words = content.split(" ");
      words =
          stream(words)
              .flatMap(word -> stream(word.splitWithDelimiters("\n", -1)))
              .toArray(String[]::new);
      for (int j = 0; j < words.length; j++) {
        final String word = words[j];
        final boolean lastWord = j == words.length - 1;
        if (word.isEmpty()) {
          continue;
        }

        final boolean isLongEnough = lineLength != 0 && lineLength + word.length() > length;
        final int newLines = countMatches(word, '\n') + (isLongEnough ? 1 : 0);
        for (int k = 0; k < newLines; ++k) {
          final String endOfLine = lineBuilder.toString();

          currentLine = currentLine.append(Component.text(endOfLine).style(style));
          wrapped.add(currentLine);

          lineLength = 0;
          currentLine = empty().style(style);
          lineBuilder = new StringBuilder();
        }

        final boolean addSpace = (!lastWord || join) && !word.endsWith("\n");
        final String cleanWord = word.replace("\n", "");
        lineBuilder.append(cleanWord).append(addSpace ? " " : "");
        lineLength += word.length() + 1;
      }

      final String endOfComponent = lineBuilder.toString();
      if (!endOfComponent.isEmpty()) {
        currentLine = currentLine.append(Component.text(endOfComponent).style(style));
      }
    }

    if (lineLength > 0) {
      wrapped.add(currentLine);
    }

    return wrapped;
  }

  private static List<TextComponent> flatten(TextComponent component) {
    final List<TextComponent> flattened = new ArrayList<>();

    final Style enforcedState = enforceStates(component.style());
    component = component.style(enforcedState);

    final Stack<TextComponent> toCheck = new Stack<>();
    toCheck.add(component);

    while (!toCheck.empty()) {
      final TextComponent parent = toCheck.pop();
      if (!parent.content().isEmpty()) {
        flattened.add(parent);
      }

      for (final Component child : parent.children().reversed()) {
        if (child instanceof TextComponent text) {
          Style style = parent.style();
          style = style.merge(child.style());
          toCheck.add(text.style(style));
        } else {
          toCheck.add(unsupported());
        }
      }
    }
    return flattened;
  }

  private static Style enforceStates(final Style style) {
    final Style.Builder builder = style.toBuilder();
    style
        .decorations()
        .forEach(
            (decoration, state) -> {
              if (state == NOT_SET) {
                builder.decoration(decoration, false);
              }
            });
    return builder.build();
  }

  private static TextComponent unsupported() {
    return Component.text("!CANNOT WRAP!").color(DARK_RED);
  }
}

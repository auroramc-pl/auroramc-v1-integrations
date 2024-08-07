package pl.auroramc.integrations.commands;

import static java.util.function.Predicate.isEqual;
import static java.util.function.Predicate.not;

import dev.rollczi.litecommands.invocation.Invocation;
import java.util.UUID;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.viewer.KyoriViewer;
import pl.auroramc.messages.viewer.Viewer;
import pl.auroramc.messages.viewer.ViewerFacade;

public final class CommandsUtils {

  private static final UUID NIL_UUID = new UUID(0, 0);

  private CommandsUtils() {}

  public static <SENDER extends Audience, VIEWER extends Viewer> Viewer getViewer(
      final ViewerFacade<VIEWER> viewerFacade, final Invocation<SENDER> invocation) {
    return invocation
        .platformSender()
        .getIdentifier()
        .getIdentifier(UUID.class)
        .filter(not(isEqual(NIL_UUID)))
        .map(viewerFacade::getOrCreateViewerByUniqueId)
        .map(Viewer.class::cast)
        .orElse(KyoriViewer.wrap(invocation.sender()));
  }
}

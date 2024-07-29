package pl.auroramc.integrations.commands;

import dev.rollczi.litecommands.invocation.Invocation;
import java.util.UUID;
import net.kyori.adventure.audience.Audience;
import pl.auroramc.messages.viewer.KyoriViewer;
import pl.auroramc.messages.viewer.Viewer;
import pl.auroramc.messages.viewer.ViewerFacade;

public final class CommandsUtils {

  private CommandsUtils() {}

  public static <SENDER extends Audience, VIEWER extends Viewer> Viewer getViewer(
      final ViewerFacade<VIEWER> viewerFacade, final Invocation<SENDER> invocation) {
    return invocation
        .platformSender()
        .getIdentifier()
        .getIdentifier(UUID.class)
        .map(viewerFacade::getViewerByUniqueId)
        .map(Viewer.class::cast)
        .orElse(KyoriViewer.wrap(invocation.sender()));
  }
}

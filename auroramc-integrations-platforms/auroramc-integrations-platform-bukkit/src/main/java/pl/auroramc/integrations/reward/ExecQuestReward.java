package pl.auroramc.integrations.reward;

import java.util.List;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class ExecQuestReward implements Reward {

  private static final String TARGET_VARIABLE_NAME = "<target>";
  private final Server server;
  private final List<String> templatesOfCommands;

  public ExecQuestReward(final Server server, final List<String> templatesOfCommands) {
    this.server = server;
    this.templatesOfCommands = templatesOfCommands;
  }

  @Override
  public void assign(final Player player) {
    getTargetedCommands(templatesOfCommands, player.getName())
        .forEach(this::executeCommandAsConsole);
  }

  private void executeCommandAsConsole(final String completeCommand) {
    server.dispatchCommand(server.getConsoleSender(), completeCommand);
  }

  private String getTargetedCommand(final String templateOfCommand, final String usernameOfTarget) {
    return templateOfCommand.replace(TARGET_VARIABLE_NAME, usernameOfTarget);
  }

  private List<String> getTargetedCommands(
      final List<String> templatesOfCommands, final String usernameOfTarget) {
    return templatesOfCommands.stream()
        .map(templateOfCommand -> getTargetedCommand(templateOfCommand, usernameOfTarget))
        .toList();
  }
}

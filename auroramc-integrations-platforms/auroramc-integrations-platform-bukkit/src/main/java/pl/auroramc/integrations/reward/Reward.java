package pl.auroramc.integrations.reward;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface Reward {

  void assign(final Player player);
}

package pl.auroramc.integrations.reward;

import static pl.auroramc.integrations.item.ItemStackUtils.giveOrDropItemStack;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemReward implements Reward {

  private final ItemStack item;

  public ItemReward(final ItemStack item) {
    this.item = item;
  }

  @Override
  public void assign(final Player player) {
    player
        .getInventory()
        .addItem(item)
        .forEach((index, remainingItem) -> giveOrDropItemStack(player, remainingItem));
  }
}

package pl.auroramc.integrations.item;

import static pl.auroramc.commons.collection.CollectionUtils.merge;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.auroramc.messages.message.compiler.CompiledMessage;

public final class ItemStackUtils {

  private ItemStackUtils() {}

  public static ItemStack getItemStackWithQuantity(final ItemStack itemStack, final int quantity) {
    final ItemStack copyOfItemStack = itemStack.clone();
    copyOfItemStack.setAmount(quantity);
    return copyOfItemStack;
  }

  public static void giveOrDropItemStack(final Player player, final ItemStack itemStack) {
    player
        .getInventory()
        .addItem(itemStack)
        .forEach(
            (index, remainingItemStack) ->
                player.getWorld().dropItemNaturally(player.getLocation(), remainingItemStack));
  }

  public static void increaseQuantityOfHeldItem(final Player player) {
    final ItemStack itemStack = player.getInventory().getItemInMainHand();
    if (itemStack.getAmount() == itemStack.getMaxStackSize()) {
      giveOrDropItemStack(player, ItemStackBuilder.newBuilder(itemStack.clone()).count(1).build());
    } else {
      itemStack.setAmount(itemStack.getAmount() + 1);
    }
  }

  public static void decreaseQuantityOfHeldItem(final Player player) {
    final ItemStack itemStack = player.getInventory().getItemInMainHand();
    if (itemStack.getAmount() == 1) {
      player.getInventory().setItemInMainHand(null);
    } else {
      itemStack.setAmount(itemStack.getAmount() - 1);
    }
  }

  public static ItemStack mergeLore(final ItemStack itemStack, final Component... lines) {
    return ItemStackBuilder.newBuilder(itemStack)
        .lore(
            merge(
                Optional.ofNullable(itemStack.lore()).orElse(Collections.emptyList()),
                List.of(lines),
                Component[]::new))
        .build();
  }

  public static ItemStack mergeLore(final ItemStack itemStack, final CompiledMessage... lines) {
    return mergeLore(
        itemStack, Stream.of(lines).map(CompiledMessage::getComponent).toArray(Component[]::new));
  }
}

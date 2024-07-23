package pl.auroramc.integrations.item;

import static java.util.Arrays.stream;
import static net.kyori.adventure.text.format.TextDecoration.ITALIC;
import static net.kyori.adventure.text.format.TextDecoration.State.FALSE;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;
import static pl.auroramc.integrations.kyori.ComponentWrapper.wrap;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import pl.auroramc.messages.message.compiler.CompiledMessage;

public final class ItemStackBuilder {

  private static final int MAXIMUM_LINE_LENGTH = 36;
  private ItemStack itemStack;
  private ItemMeta itemMeta;

  private ItemStackBuilder(final ItemStack itemStack) {
    this.itemStack = itemStack;
    this.itemMeta = itemStack.getItemMeta();
  }

  public static ItemStackBuilder newBuilder(final ItemStack itemStack) {
    return new ItemStackBuilder(itemStack.clone());
  }

  public static ItemStackBuilder newBuilder(final Material material) {
    return newBuilder(new ItemStack(material));
  }

  public ItemStackBuilder displayName(final Component displayName) {
    itemMeta.displayName(displayName);
    return this;
  }

  public ItemStackBuilder displayName(final CompiledMessage displayName) {
    return displayName(displayName.getComponent());
  }

  public ItemStackBuilder displayName(final String unparsedDisplayName) {
    return displayName(miniMessage().deserialize(unparsedDisplayName).decoration(ITALIC, FALSE));
  }

  public ItemStackBuilder type(final Material type) {
    itemStack = itemStack.withType(type);
    itemMeta = itemStack.getItemMeta();
    return this;
  }

  public ItemStackBuilder count(final int count) {
    itemStack.setAmount(count);
    return this;
  }

  public ItemStackBuilder lore(final Component... lines) {
    itemMeta.lore(Stream.of(lines).flatMap(line -> wrap(line, MAXIMUM_LINE_LENGTH).stream()).toList());
    return this;
  }

  public ItemStackBuilder lore(final String... lines) {
    return lore(
        resolveComponent(line -> miniMessage().deserialize(line).decoration(ITALIC, FALSE), lines));
  }

  public ItemStackBuilder lore(final CompiledMessage... lines) {
    return lore(resolveComponent(CompiledMessage::getComponent, lines));
  }

  public <K, V> ItemStackBuilder data(
      final NamespacedKey key, final PersistentDataType<K, V> type, final V value) {
    itemMeta.getPersistentDataContainer().set(key, type, value);
    return this;
  }

  public ItemStackBuilder flags(final ItemFlag... flags) {
    itemMeta.addItemFlags(flags);
    return this;
  }

  public ItemStackBuilder enchantment(final Enchantment enchantment, final int level) {
    itemMeta.addEnchant(enchantment, level, enchantment.getMaxLevel() < level);
    return this;
  }

  public ItemStackBuilder manipulate(final Consumer<ItemStackBuilder> manipulation) {
    manipulation.accept(this);
    return this;
  }

  public <T> ItemStackBuilder manipulate(
      final Predicate<T> condition, final T value, final Consumer<ItemStackBuilder> manipulator) {
    return condition.test(value) ? manipulate(manipulator) : this;
  }

  public ItemStack build() {
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  private @SafeVarargs <T> Component[] resolveComponent(
      final Function<T, Component> retriever, final T... values) {
    return stream(values).map(retriever).toArray(Component[]::new);
  }
}

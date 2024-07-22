package pl.auroramc.integrations.reward;

import static groovy.lang.Closure.DELEGATE_ONLY;
import static org.bukkit.Bukkit.getServer;
import static org.bukkit.Material.STONE;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import pl.auroramc.integrations.item.ItemStackBuilder;

public class RewardsDsl {

  private final List<Reward> rewards;

  public RewardsDsl() {
    this.rewards = new ArrayList<>();
  }

  public void item(final ItemStack item) {
    rewards.add(new ItemReward(item));
  }

  public void item(final @DelegatesTo(ItemStackBuilder.class) Closure<?> closure) {
    final ItemStackBuilder delegate = ItemStackBuilder.newBuilder(STONE);
    closure.setDelegate(delegate);
    closure.setResolveStrategy(DELEGATE_ONLY);
    closure.call();
    rewards.add(new ItemReward(delegate.build()));
  }

  public void exec(final List<String> commands) {
    rewards.add(new ExecReward(getServer(), commands));
  }

  public void exec(final String... commands) {
    exec(List.of(commands));
  }

  public List<Reward> rewards() {
    return rewards;
  }
}

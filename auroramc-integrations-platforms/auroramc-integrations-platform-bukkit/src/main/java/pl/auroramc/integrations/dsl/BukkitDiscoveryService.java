package pl.auroramc.integrations.dsl;

import java.util.stream.Stream;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

public class BukkitDiscoveryService<T> extends DiscoveryService<T> {

  private static final String[] IMPORT_CLASS_NAMES =
      Stream.of(Material.class, ItemFlag.class, EntityType.class, Enchantment.class)
          .map(Class::getName)
          .toArray(String[]::new);

  public BukkitDiscoveryService(
      final ClassLoader parentClassLoader, final Class<T> elementType) {
    super(parentClassLoader, elementType);
  }

  @Override
  public ImportCustomizer getImportCustomizer() {
    final ImportCustomizer importCustomizer = new ImportCustomizer();
    importCustomizer.addImports(IMPORT_CLASS_NAMES);
    return importCustomizer;
  }
}

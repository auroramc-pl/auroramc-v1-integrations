package pl.auroramc.integrations.configs.serdes.standard;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import java.util.Map.Entry;
import java.util.Properties;
import org.jetbrains.annotations.NotNull;

class PropertiesSerializer implements ObjectSerializer<Properties> {

  @Override
  public boolean supports(final @NotNull Class<? super Properties> type) {
    return Properties.class.isAssignableFrom(type);
  }

  @Override
  public void serialize(
      final @NotNull Properties object,
      final @NotNull SerializationData data,
      final @NotNull GenericsDeclaration generics) {
    for (final Entry<Object, Object> objectObjectEntry : object.entrySet()) {
      data.add(objectObjectEntry.getKey().toString(), objectObjectEntry.getValue());
    }
  }

  @Override
  public Properties deserialize(
      final @NotNull DeserializationData data, final @NotNull GenericsDeclaration generics) {
    final Properties properties = new Properties();
    properties.putAll(data.asMap());
    return properties;
  }
}

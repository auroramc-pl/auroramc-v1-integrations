package pl.auroramc.integrations.configs.serdes.juliet;

import com.zaxxer.hikari.HikariConfig;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import java.util.Properties;
import org.jetbrains.annotations.NotNull;

class HikariConfigSerializer implements ObjectSerializer<HikariConfig> {

  private static final String JDBC_URL_VARIABLE_KEY = "jdbcUrl";
  private static final String USERNAME_VARIABLE_KEY = "username";
  private static final String PASSWORD_VARIABLE_KEY = "password";
  private static final String DRIVER_CLASS_NAME_VARIABLE_KEY = "driverClassName";
  private static final String DATA_SOURCE_PROPERTIES_KEY = "dataSourceProperties";
  private static final String MAXIMUM_POOL_SIZE = "maximumPoolSize";
  private static final int UNDEFINED_POOL_SIZE = -1;

  HikariConfigSerializer() {}

  @Override
  public boolean supports(final @NotNull Class<? super HikariConfig> type) {
    return HikariConfig.class.isAssignableFrom(type);
  }

  @Override
  public void serialize(
      final @NotNull HikariConfig object,
      final @NotNull SerializationData data,
      final @NotNull GenericsDeclaration generics) {
    data.add(JDBC_URL_VARIABLE_KEY, object.getJdbcUrl());

    if (object.getUsername() != null) {
      data.add(USERNAME_VARIABLE_KEY, object.getUsername());
    }

    if (object.getPassword() != null) {
      data.add(PASSWORD_VARIABLE_KEY, object.getPassword());
    }

    if (object.getDriverClassName() != null) {
      data.add(DRIVER_CLASS_NAME_VARIABLE_KEY, object.getDriverClassName());
    }

    if (object.getMaximumPoolSize() != UNDEFINED_POOL_SIZE) {
      data.add(MAXIMUM_POOL_SIZE, object.getMaximumPoolSize());
    }

    if (!object.getDataSourceProperties().isEmpty()) {
      data.add(DATA_SOURCE_PROPERTIES_KEY, object.getDataSourceProperties());
    }
  }

  @Override
  public HikariConfig deserialize(
      final @NotNull DeserializationData data, final @NotNull GenericsDeclaration generics) {
    final HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(data.get(JDBC_URL_VARIABLE_KEY, String.class));

    if (data.containsKey(USERNAME_VARIABLE_KEY)) {
      hikariConfig.setUsername(data.get(USERNAME_VARIABLE_KEY, String.class));
    }

    if (data.containsKey(PASSWORD_VARIABLE_KEY)) {
      hikariConfig.setPassword(data.get(PASSWORD_VARIABLE_KEY, String.class));
    }

    if (data.containsKey(DRIVER_CLASS_NAME_VARIABLE_KEY)) {
      hikariConfig.setDriverClassName(data.get(DRIVER_CLASS_NAME_VARIABLE_KEY, String.class));
    }

    if (data.containsKey(MAXIMUM_POOL_SIZE)) {
      hikariConfig.setMaximumPoolSize(data.get(MAXIMUM_POOL_SIZE, Integer.class));
    }

    if (data.containsKey(DATA_SOURCE_PROPERTIES_KEY)) {
      hikariConfig.setDataSourceProperties(data.get(DATA_SOURCE_PROPERTIES_KEY, Properties.class));
    }

    return hikariConfig;
  }
}

package pl.auroramc.integrations.configs.juliet;

import com.zaxxer.hikari.HikariConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Exclude;

public class JulietConfig extends OkaeriConfig {

  public static final @Exclude String JULIET_CONFIG_FILE_NAME = "juliet.yml";

  public HikariConfig hikari = getDefaultHikariConfig();

  private HikariConfig getDefaultHikariConfig() {
    final HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");
    hikariConfig.setJdbcUrl("jdbc:mariadb://localhost:3306/auroramc_dev");
    hikariConfig.setUsername("auroramc_identity");
    hikariConfig.setPassword("my-secret-password-123-!@#");
    hikariConfig.setMaximumPoolSize(10);

    hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

    // https://vladmihalcea.com/mysql-rewritebatchedstatements/
    hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");

    return hikariConfig;
  }
}

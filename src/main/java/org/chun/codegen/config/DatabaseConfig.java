package org.chun.codegen.config;

import org.chun.codegen.common.constant.CodegenConst;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@MapperScan(basePackages = CodegenConst.DAO_PACKAGE_PATH,
    sqlSessionFactoryRef = CodegenConst.SQL_SESSION_FACTORY)
@EnableTransactionManagement
@Configuration
public class DatabaseConfig {

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

}

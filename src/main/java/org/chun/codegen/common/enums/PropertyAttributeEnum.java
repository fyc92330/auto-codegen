package org.chun.codegen.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class PropertyAttributeEnum {

  @Getter
  @AllArgsConstructor
  public enum Profile {

    DESC("## Spring", "", "0"),
    ACTIVE("spring.profiles.active", "%s", "1");

    private final String attributeName;
    private final String defaultValueFormat;
    private final String index;
  }

  @Getter
  @AllArgsConstructor
  public enum FilePath {

    OUT_PUT_DESC("## output","","0"),
    OUT_PUT_MAIN("output.main_dir","","1"),
    OUT_PUT_DAO("output.dao_path","","2"),
    OUT_PUT_VO("output.vo_path","","3"),
    OUT_PUT_MAPPER("output.mapper_path","","4"),
    PACKAGE_DESC("## package","","5"),
    PACKAGE_BASE_DAO("package.dao_base","","6"),
    PACKAGE_DAO("package.dao_extend","","7"),
    PACKAGE_BASE_VO("package.vo_base","","8"),
    PACKAGE_VO("package.vo_extend","","9"),
    PACKAGE_BASE_MAPPER("package.mapper_base","","10"),
    PACKAGE_MAPPER("package.mapper_extend","","11"),
    TEMPLATE_DESC("## template","","12"),
    TEMPLATE_DAO("template.dao_extend","extendDao.ftl","14"),
    TEMPLATE_BASE_DAO("template.dao_base","baseDao.ftl","13"),
    TEMPLATE_BASE_VO("template.vo_base","baseVo.ftl","15"),
    TEMPLATE_VO("template.vo_extend","extendVo.ftl","16"),
    TEMPLATE_BASE_MAPPER("template.mapper_base","baseMapper.ftl","17"),
    TEMPLATE_MAPPER("template.mapper_extend","extendMapper.ftl","18");

    private final String attributeName;
    private final String defaultValueFormat;
    private final String index;
  }

  @Getter
  @AllArgsConstructor
  public enum DataSource {

    DESC("## DataSource Config", "", "0"),
    DRIVER_CLASS_NAME("spring.datasource.driver_class_name", "org.postgresql.Driver", "1"),
    NAME("spring.datasource.name", "", "2"),
    // host, port, name,
    URL("spring.datasource.url", "jdbc:postgresql://%s:%s/%s?stringtype=unspecified&autosave=always", "3"),
    USERNAME("spring.datasource.username", "", "4"),
    PASSWORD("spring.datasource.password", "", "5");

    private final String attributeName;
    private final String defaultValueFormat;
    private final String index;
  }

  @Getter
  @AllArgsConstructor
  public enum MyBatis {

    DESC("## MyBatis", "", "0"),
    CONFIG_LOCATION("mybatis.check-config-location", "true", "1"),
    MAPPER_LOCATION("mybatis.mapper-locations", "classpath*:%s/**/*.xml", "2"),
    ALIAS_PACKAGE("mybatis.type-aliases-package", "", "3"),
    LAZY("mybatis.lazy-initialization", "true", "4"),
    UNDERSCORE_2_CAMEL("mybatis.configuration.map-underscore-to-camel-case", "true", "5"),
    KEY("mybatis.configuration.use-generated-keys", "true", "6"),
    LAZY_ENABLED("mybatis.configuration.lazy-loading-enabled", "true", "7"),
    LAZY_AGGRESSIVE("mybatis.configuration.aggressive-lazy-loading", "false", "8"),
    TYPE_FOR_NULL("mybatis.configuration.jdbc-type-for-null", "null", "9");

    private final String attributeName;
    private final String defaultValueFormat;
    private final String index;
  }
}

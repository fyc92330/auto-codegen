package org.chun.codegen.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "package")
public record PackageProperties(
    String daoBase,
    String daoExtend,
    String voBase,
    String voExtend,
    String mapperBase,
    String mapperExtend) {
}

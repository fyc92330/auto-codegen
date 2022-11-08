package org.chun.codegen.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "template")
public record TemplateProperties(
    String daoBase,
    String daoExtend,
    String voBase,
    String voExtend,
    String mapperBase,
    String mapperExtend) {
}

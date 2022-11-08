package org.chun.codegen.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "output")
public record OutputProperties(
    String mainDir,
    String daoPath,
    String voPath,
    String mapperPath) {
}
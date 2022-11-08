package org.chun.codegen.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.codegen.common.vo.CodegenEnvironment;
import org.chun.codegen.props.OutputProperties;
import org.chun.codegen.props.PackageProperties;
import org.chun.codegen.props.TemplateProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@EnableConfigurationProperties(value = {PackageProperties.class, TemplateProperties.class, OutputProperties.class})
@RequiredArgsConstructor
@Configuration
public class CodegenConfig {

  private final PackageProperties packageProp;
  private final TemplateProperties templateProp;
  private final OutputProperties outputProp;

  @Bean
  public CodegenEnvironment codegenEnvironment() {
    return new CodegenEnvironment(
        packageProp.voBase(),
        packageProp.voExtend(),
        packageProp.daoBase(),
        packageProp.daoExtend(),
        packageProp.mapperBase(),
        packageProp.mapperExtend(),
        templateProp.voBase(),
        templateProp.voExtend(),
        templateProp.daoBase(),
        templateProp.daoExtend(),
        templateProp.mapperBase(),
        templateProp.mapperExtend(),
        outputProp.mainDir(),
        outputProp.voPath(),
        outputProp.daoPath(),
        outputProp.mapperPath()
    );
  }
}

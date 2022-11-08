package org.chun.codegen.config;

import lombok.extern.slf4j.Slf4j;
import org.chun.codegen.common.constant.CodegenConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

@Slf4j
@Configuration
public class FreeMarkerConfig {

  @Bean
  public FreeMarkerConfigurationFactory getFreeMarkerConfigurationFactory() {
    FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
    factory.setPreferFileSystemAccess(false);
    factory.setDefaultEncoding(CodegenConst.PROJECT_DEFAULT_ENCODING);
    factory.setTemplateLoaderPath(CodegenConst.BUILD_TEMPLATE_PATH);
    return factory;
  }

}

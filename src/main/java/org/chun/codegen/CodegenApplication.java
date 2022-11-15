package org.chun.codegen;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.codegen.service.CodegenService;
import org.chun.codegen.service.IProgrammingService;
import org.chun.codegen.service.PropertyGenService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class CodegenApplication implements CommandLineRunner {
  private final boolean isBuildingMode;
  private final CodegenService codegenService;
  private final PropertyGenService propertyGenService;
  private final ConfigurableApplicationContext application;

  public static void main(String[] args) {
    SpringApplication.run(CodegenApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("開始執行codegen程式");
    this.gateway().main();
    SpringApplication.exit(application);
  }

  /**
   * 變更 創建者模式 與 佇立者模式
   *
   * @return
   */
  private IProgrammingService gateway() {
    return isBuildingMode
        ? codegenService
        : propertyGenService;
  }
}

package org.chun.codegen;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.codegen.service.CodegenService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.Scanner;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class CodegenApplication implements CommandLineRunner {
  private static final String TABLE_NAME_REGEX = "^[a-zA-Z][a-zA-Z0-9]*([_]{1}[a-zA-Z0-9]+)*([,]{1}[Ll]{1})?";
  private static final String[] exitCommandArray = new String[]{"exit", "-q"};
  private final CodegenService codegenService;
  private final ConfigurableApplicationContext application;

  public static void main(String[] args) {
    SpringApplication.run(CodegenApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("開始執行codegen程式, 輸入建立的table, 或輸入exit離開");
    try (Scanner scanner = new Scanner(System.in)) {
      while (scanner.hasNextLine()) {
        String input = scanner.nextLine().trim().toLowerCase();
        // 離開程式
        if (Arrays.asList(exitCommandArray).contains(input)) {
          break;
        }
        // 檢核輸入
        if (input.isBlank() || !input.matches(TABLE_NAME_REGEX)) {
          log.info("格式錯誤, 請輸入table名稱");
          continue;
        }
        // 建立檔案
        codegenService.codeGenerate(input);
      }

    } catch (Exception e) {
      log.error("", e);
    } finally {
      log.info("程式結束");
      SpringApplication.exit(application);
    }
  }
}

package org.chun.codegen.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.codegen.common.enums.PropertyAttributeEnum;
import org.chun.codegen.util.InputValidatorUtil;
import org.chun.codegen.util.StringUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
@Service
public class PropertyGenService implements IProgrammingService {

  private static final String FORMAT_SYMBOL_STR = "%s";
  private static final String OUTPUT_FOLDER_PREFIX_FORMAT = "/src/main/%s";

  /**
   * 佇立者模式
   */
  @Override
  public void main() throws Exception {
    log.info("佇立者模式: 輸入對應的路徑, 或輸入exit離開. 或是輸入back重新設置");
    try (Scanner scanner = new Scanner(System.in)) {
      // 建立文件
      File propertyFile = this.createPropertyFile(scanner);
      // 紀錄參數
      Map<String, String> propertyContent = this.propertyParamHandler(propertyFile.getName(), scanner);
      // 寫入內容
      this.writePropertyParams(propertyContent, propertyFile, scanner);
      log.info("配置檔案生成完畢, 請重新啟動程式.");
    } catch (Exception e) {
      log.error("", e);
    }

  }

  /** =================================================== private ================================================== */

  /**
   * 建立文件
   *
   * @param scanner
   * @return
   */
  private File createPropertyFile(Scanner scanner) {
    log.info("請輸入[profile]身份");
    final String profile = scanner.nextLine();
    ClassPathResource resource = new ClassPathResource(StringUtil.genPropertyFileName(profile));
    if (resource.exists()) {
      throw new RuntimeException(String.format("身份名稱[%s] 已經存在, 請選擇其他名稱.", profile));
    }

    log.info("生成配置檔案[application-{}.properties]", profile);
    return new File(resource.getPath());
  }

  /**
   * 寫入內容
   *
   * @param params
   * @param propertyFile
   * @param scanner
   */
  private void writePropertyParams(Map<String, String> params, File propertyFile, Scanner scanner) {
    log.info("param: {}", params);

    StringBuilder sb = new StringBuilder();
    params.entrySet().stream()
        .map(entry -> String.format("%s=%s%n", entry.getKey(), entry.getValue()))
        .forEach(sb::append);

    try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(propertyFile), StandardCharsets.UTF_8)) {
      writer.write(sb.toString());
      log.info("");
      log.info(">>>>>>> 檔案寫入完成 <<<<<<<");
      log.info("");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 處理參數寫入
   *
   * @param fileName
   * @param scanner
   * @return
   */
  private Map<String, String> propertyParamHandler(String fileName, Scanner scanner) {
    // Profile
    Map<String, String> params = this.profileSetting(fileName);
    // FilePath 參數

    // DataSource 參數
    this.check2Reset(() -> this.myBatisSetting(scanner), params, scanner);
    // MyBatis 參數
    this.check2Reset(() -> this.dataSourceSetting(scanner), params, scanner);

    return params;
  }

  private void check2Reset(Supplier<Map<String, String>> func, Map<String, String> resultMap, Scanner scanner) {
    boolean isFinish = false;
    do {
      Map<String, String> tempMap = func.get();

      log.info("參數輸入完畢, 是否需要重新輸入?(Y/N) ");
      if (StringUtils.startsWithIgnoreCase("Y", (InputValidatorUtil.inputBool(scanner)))) {
        isFinish = true;
      } else {
        resultMap.putAll(tempMap);
      }
    } while (isFinish);
  }

  private Map<String, String> profileSetting(String fileName) {
    Map<String, String> tempMap = new LinkedHashMap<>();
    Arrays.stream(PropertyAttributeEnum.Profile.values())
        .map(e -> {
          String value = e.getDefaultValueFormat();
          return new String[]{
              e.getAttributeName(),
              value.contains(FORMAT_SYMBOL_STR)
                  ? StringUtil.getActiveProfile(fileName)
                  : value,
              e.getIndex()
          };
        })
        .sorted(Comparator.comparing(arr -> arr[2]))
        .forEach(arr -> tempMap.put(arr[0], arr[1]));
    log.info("");
    log.info(">>>>>>> Profile 資訊設定完成 <<<<<<<");
    log.info("");
    return tempMap;
  }

  private Map<String, String> dataSourceSetting(Scanner scanner) {
    Map<String, String> tempMap = new LinkedHashMap<>();

    log.info("請輸入資料庫路徑(host):");
    String host = scanner.nextLine();
    log.info("請輸入資料庫埠號(port):");
    String port = scanner.nextLine();
    log.info("請輸入資料庫名稱(name):");
    String name = scanner.nextLine();
    log.info("請輸入資料庫使用者名稱(username):");
    String username = scanner.nextLine();
    log.info("請輸入資料庫使用者密碼(password):");
    String password = scanner.nextLine();

    Arrays.stream(PropertyAttributeEnum.DataSource.values())
        .map(e -> {
          String value = e.getDefaultValueFormat();
          return new String[]{
              e.getAttributeName(),
              switch (e) {
                case NAME -> name;
                case USERNAME -> username;
                case PASSWORD -> password;
                default -> value.contains(FORMAT_SYMBOL_STR)
                    ? String.format(value, host, port, name)
                    : value;
              },
              e.getIndex()
          };
        })
        .sorted(Comparator.comparing(arr -> arr[2]))
        .forEach(arr -> tempMap.put(arr[0], arr[1]));
    log.info("");
    log.info(">>>>>>> DataSource 資訊設定完成 <<<<<<<");
    log.info("");
    return tempMap;
  }

  private Map<String, String> myBatisSetting(Scanner scanner, String... packagePaths) {
    Map<String, String> tempMap = new LinkedHashMap<>();
    StringBuilder sb = new StringBuilder();
    Arrays.stream(packagePaths).map(path -> path.concat(";")).forEach(sb::append);
    String packagePath = sb.toString();

    log.info("請輸入對應[Mapper]的存放路徑:");
    String mapperPath = scanner.nextLine();

    try {
      Resource resource = new ClassPathResource(StringUtil.genPropertyResourceFolder(mapperPath));
      File file = resource.getFile();
      if (!(file.exists() && file.isDirectory()) && file.mkdir()) {
        log.info("[Mapper]資料夾({})建立完成.", mapperPath);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Arrays.stream(PropertyAttributeEnum.MyBatis.values())
        .map(e -> {
          String value = e.getDefaultValueFormat();
          return new String[]{
              e.getAttributeName(),
              switch (e) {
                case MAPPER_LOCATION -> String.format(value, mapperPath);
                case ALIAS_PACKAGE -> packagePath;
                default -> value;
              },
              e.getIndex()
          };
        })
        .sorted(Comparator.comparing(arr -> arr[2]))
        .forEach(arr -> tempMap.put(arr[0], arr[1]));
    log.info("");
    log.info(">>>>>>> MyBatis 資訊設定完成 <<<<<<<");
    log.info("");
    return tempMap;
  }

  private Map<String, String> outputFileSetting(Scanner scanner) {
    Map<String, String> tempMap = new LinkedHashMap<>();

    log.info("請輸入專案ID (ex. com.example.demo): ");
    String projectId = scanner.nextLine();
    String projectDir = projectId;

    log.info("請輸入專案資料夾位置: (ex. Users/user/project)");
    String mainDir = scanner.nextLine();

    log.info("請輸入專案下vo資料夾位置: (ex. common/vo)");
    String voDir = scanner.nextLine();

    log.info("請輸入專案下dao資料夾位置: (ex. common/dao)");
    String daoDir = scanner.nextLine();

    log.info("請輸入專案下mapper資料夾位置: (ex. mybatis/mapper)");
    String mapperDir = scanner.nextLine();

    Arrays.stream(PropertyAttributeEnum.FilePath.values())
        .map(e -> {
          String value = e.getDefaultValueFormat();
          return new String[]{
              e.getAttributeName(),
              switch (e) {
                case OUT_PUT_MAIN -> mainDir;
                case OUT_PUT_VO -> projectDir.concat(voDir);
                case OUT_PUT_DAO -> projectDir.concat(daoDir);
                case OUT_PUT_MAPPER -> projectDir.concat(mapperDir);
//                case PACKAGE_VO ->
//                case PACKAGE_BASE_VO ->
//                case PACKAGE_DAO ->
//                case PACKAGE_BASE_DAO ->
//                case PACKAGE_MAPPER ->
//                case PACKAGE_BASE_MAPPER ->
                default -> value;
              },
              e.getIndex()
          };
        })
        .sorted(Comparator.comparing(arr -> arr[2]))
        .forEach(arr -> tempMap.put(arr[0], arr[1]));

  }

}

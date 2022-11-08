package org.chun.codegen.service;

import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.chun.codegen.common.dao.MetaDao;
import org.chun.codegen.common.enums.GenTypeEnum;
import org.chun.codegen.common.exception.TableNotFoundException;
import org.chun.codegen.common.vo.CodegenEnvironment;
import org.chun.codegen.common.vo.CodegenModel;
import org.chun.codegen.common.vo.MetaTable;
import org.chun.codegen.common.vo.TablePrimaryKeyConstraint;
import org.chun.codegen.util.JavaCodeUtil;
import org.chun.codegen.util.StringUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class CodegenService {

  private static final String BASE_FOLDER_PATH = "/base";
  private final MetaDao metaDao;
  private final CodegenEnvironment env;
  private final freemarker.template.Configuration config;

  /**
   * 製作coding物件
   *
   * @param tableName
   */
  public void codeGenerate(String tableName) {
    this.mkdir();
    try {
      MetaTable table = this.getMetaTable(tableName);
      for (GenTypeEnum genTypeEnum : GenTypeEnum.values()) {
        CodegenModel data = new CodegenModel(table, genTypeEnum, env);
        String fileName = this.fileName(table, genTypeEnum);
        Path filePath = this.filePath(fileName, env, genTypeEnum);
        File file = filePath.toFile();
        final boolean isFileNotExists = !file.exists();
        if (isFileNotExists) {
          file = new File(filePath.toString());
        }
        log.error("------- {}{}", fileName, isFileNotExists ? "不存在" : "存在");
        if (isFileNotExists || genTypeEnum.overwriteIfExists()) {
          try {
            this.output(data, file, env, genTypeEnum);
          } catch (Exception e) {
            log.error("------- {}輸出失敗", fileName, e);
          }
        }
      }
      log.info("{}輸出成功", tableName);
    } catch (TableNotFoundException e) {
      log.error("{}不存在", tableName);
    }

    log.info("輸入建立的table, 或輸入exit離開");
  }

  /**
   * 取得Table
   *
   * @param tableName
   * @return
   */
  private MetaTable getMetaTable(String tableName) {
    return metaDao.listTableWithColumn(tableName).stream()
        .findAny()
        .map(this::getMetaTable)
        .orElseThrow(() -> new TableNotFoundException(tableName));
  }

  /**
   * 標示identity column
   *
   * @param table
   * @return
   */
  private MetaTable getMetaTable(MetaTable table) {
    if (JavaCodeUtil.validJavaIdentifier(table)) {
      TablePrimaryKeyConstraint tablePrimaryKey = metaDao.getTablePrimaryKey(table.getObjectName());
      if (this.isAutoIncrement(tablePrimaryKey)) {
        String columnName = tablePrimaryKey.getColumnName();
        table.getColumns().stream()
            .filter(column -> column.getColumnName().equals(columnName))
            .findAny()
            .ifPresent(column -> column.setAutoIncrement(true));
      }
    }
    return table;
  }

  /**
   * 檢核是否有自動生成
   *
   * @param tablePrimaryKey
   * @return
   */
  private boolean isAutoIncrement(TablePrimaryKeyConstraint tablePrimaryKey) {
    final String columnDefault = tablePrimaryKey.getColumnDefault();
    return "YES".equals(tablePrimaryKey.getIsIdentity()) ||
        (Strings.isNotBlank(columnDefault) && columnDefault.startsWith("nextval('"));
  }

  /**
   * 檔案名稱
   *
   * @param table
   * @param genTypeEnum
   * @return
   */
  private String fileName(MetaTable table, GenTypeEnum genTypeEnum) {
    String tableName = table.getObjectName();
    String fileName = switch (genTypeEnum) {
      case VO, BASE_VO, DAO, BASE_DAO -> StringUtil.underscore2Cobra(tableName);
      case MAPPER, BASE_MAPPER -> StringUtil.underscore2Camel(tableName);
    };
    return fileName.concat(genTypeEnum.getBaseFileName());
  }

  /**
   * 檔案路徑
   *
   * @param fileName
   * @param env
   * @param genTypeEnum
   * @return
   */
  private Path filePath(String fileName, CodegenEnvironment env, GenTypeEnum genTypeEnum) {
    String outputPath = switch (genTypeEnum) {
      case VO, BASE_VO -> env.getOutputVoPath();
      case DAO, BASE_DAO -> env.getOutputDaoPath();
      case MAPPER, BASE_MAPPER -> env.getOutputMapperPath();
    };

    if (GenTypeEnum.BASE_VO == genTypeEnum ||
        GenTypeEnum.BASE_DAO == genTypeEnum ||
        GenTypeEnum.BASE_MAPPER == genTypeEnum) {
      outputPath = outputPath.concat(BASE_FOLDER_PATH);
    }

    return Paths.get(env.getOutputMainDir(), outputPath, fileName);
  }

  /**
   * 檔案輸出
   *
   * @param model
   * @param file
   * @param env
   * @param genTypeEnum
   * @throws Exception
   */
  private void output(CodegenModel model, File file, CodegenEnvironment env, GenTypeEnum genTypeEnum) throws Exception {
    try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
      Template template = config.getTemplate(
          switch (genTypeEnum) {
            case BASE_DAO -> env.getTemplateDaoBase();
            case BASE_MAPPER -> env.getTemplateMapperBase();
            case BASE_VO -> env.getTemplateVoBase();
            case DAO -> env.getTemplateDaoExtend();
            case MAPPER -> env.getTemplateMapperExtend();
            case VO -> env.getTemplateVoExtend();
          }
      );
      template.process(model, writer);
    }
  }

  /**
   * 建立對應資料夾
   */
  private void mkdir() {
    // 檢核專案資料夾
    String mainDir = env.getOutputMainDir();
    this.validDirectoryExists(mainDir, true);

    // 生成對應資料夾設定
    Consumer<String> genDirectoryIfNotExists = p -> Stream.of(p, p.concat(BASE_FOLDER_PATH))
        .forEach(path -> this.validDirectoryExists(path, false));

    Stream.of(env.getOutputDaoPath(), env.getOutputVoPath(), env.getOutputMapperPath())
        .map(mainDir::concat)
        .forEach(genDirectoryIfNotExists);
  }

  /**
   * 檢核資料夾存在
   *
   * @param filePath
   * @param isMainDir
   */
  private void validDirectoryExists(String filePath, boolean isMainDir) {
    File file = new File(filePath);
    if (file.exists() && file.isDirectory()) {
      return;
    }

    if (isMainDir) {
      throw new RuntimeException(String.format("專案資料夾設定錯誤, %s", file.getPath()));
    } else {
      file.mkdir();
    }
  }
}

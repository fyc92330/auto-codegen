package org.chun.codegen.util;

import org.chun.codegen.common.enums.GenTypeEnum;
import org.chun.codegen.common.vo.CodegenEnvironment;
import org.chun.codegen.common.vo.MetaTable;
import org.chun.codegen.common.vo.MetaTableColumn;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.chun.codegen.util.StringUtil.underscore2Cobra;

public class JavaCodeUtil {

  private static final String DOT_STR = ".";
  private static final String VO_SUFFIX = "Vo";
  private static final String BASE_VO_SUFFIX = "BaseVo";
  private static final String BASE_DAO_SUFFIX = "BaseDao";
  private static final String JAVA_IMPORT_FORMAT = "%s.%s%s";
  private static final Pattern identifierPattern = Pattern.compile("^[_$a-zA-Z][_$a-zA-Z\\d]+$");
  private static final List<String> keywordList = Arrays.asList(
      "int", "abstract", "continue", "for", "new",
      "switch", "assert", "default", "goto", "package",
      "synchronized", "boolean", "do", "if", "private",
      "this", "break", "double", "implements", "protected",
      "throw", "byte", "else", "import", "public",
      "throws", "case", "catch", "char", "class",
      "const", "enum", "extends", "final", "finally",
      "float", "native", "long", "interface", "instanceof",
      "super", "strictfp", "static", "short", "return",
      "while", "volatile", "void", "try", "transient");

  public static boolean validJavaIdentifier(MetaTable table) {
    String cobraName = underscore2Cobra(table.getObjectName());
    Predicate<String> validIdentifier = JavaCodeUtil::validJavaIdentifier;
    return validJavaIdentifier(cobraName) &&
        isJavaKeyword(cobraName) &&
        table.getColumns().stream()
            .map(MetaTableColumn::getColumnName)
            .map(StringUtil::underscore2Cobra)
            .allMatch(validIdentifier.and(JavaCodeUtil::isJavaKeyword));
  }

  public static boolean validJavaIdentifier(String raw) {
    return identifierPattern.matcher(raw).find();
  }

  public static boolean isJavaKeyword(String raw) {
    return keywordList.contains(raw);
  }

  public static List<String> getJavaImport(MetaTable tableDetail, CodegenEnvironment env, GenTypeEnum genTypeEnum) {
    final String tableName = StringUtil.underscore2Cobra(tableDetail.getObjectName());

    return switch (genTypeEnum) {
      case BASE_VO -> createJavaImport(tableDetail);
      case VO -> createJavaImport(String.format(JAVA_IMPORT_FORMAT, env.getPackageVoBase(), tableName, BASE_VO_SUFFIX));
      case BASE_DAO -> createJavaImport(String.format(JAVA_IMPORT_FORMAT, env.getPackageVoExtend(), tableName, VO_SUFFIX));
      case DAO -> createJavaImport(String.format(JAVA_IMPORT_FORMAT, env.getPackageVoBase(), tableName, BASE_DAO_SUFFIX));
      default -> throw new IllegalStateException("Unexpected value: " + genTypeEnum);
    };
  }

  private static List<String> createJavaImport(MetaTable tableDetail) {
    return tableDetail.getColumns().stream()
        .map(MetaTableColumn::getDataType)
        .filter(t -> t.contains(DOT_STR))
        .distinct()
        .collect(Collectors.toList());
  }

  private static List<String> createJavaImport(String javaImport) {
    return Collections.singletonList(javaImport);
  }

}

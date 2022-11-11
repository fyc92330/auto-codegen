package org.chun.codegen.common.constant;

public class CodegenConst {
  public static final String DAO_PACKAGE_PATH = "org.chun.codegen.common.dao";
  public static final String PROJECT_DEFAULT_ENCODING = "UTF-8";
  public static final String BUILD_TEMPLATE_PATH = "classpath:template";
  public static final String SQL_SESSION_FACTORY = "sqlSessionFactory";
  public static final String TABLE_NAME_REGEX = "^[a-zA-Z][a-zA-Z0-9]*([_]{1}[a-zA-Z0-9]+)*([,]{1}[Ll]{1})?";
  public static final String[] EXIT_COMMAND_ARRAY = new String[]{"exit", "-q"};
}

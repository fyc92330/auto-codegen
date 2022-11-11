package org.chun.codegen.util;

import lombok.NonNull;

public class StringUtil {

  private static final String UNDERLINE = "_";

  public static String genPropertyFileName(String profile) {
    return String.format("src/main/resources/application-%s.properties", profile);
  }

  public static String genPropertyResourceFolder(String name) {
    return String.format("src/main/resources/%s", name);
  }

  public static String getActiveProfile(String propertyFileName) {
    StringBuilder sb = new StringBuilder();
    boolean flag = false;
    for (char c : propertyFileName.toCharArray()) {
      if ('.' == c) {
        break;
      } else if (flag) {
        sb.append(c);
      } else if ('-' == c) {
        flag = true;
      }
    }

    return sb.toString();
  }

  public static String underscore2Cobra(@NonNull String underscore) {
    checkInputString(underscore);
    return handleString(underscore.toCharArray(), true);
  }

  public static String underscore2Camel(@NonNull String underscore) {
    checkInputString(underscore);
    return handleString(underscore.toCharArray(), false);
  }

  private static String handleString(char[] underscore, boolean cobra) {
    StringBuilder sb = new StringBuilder();
    boolean underline = false;
    for (int i = 0; i < underscore.length; i++) {
      String feature = String.valueOf(underscore[i]);
      if (UNDERLINE.equals(feature)) {
        underline = true;
        continue;
      }

      if (i == 0) {
        feature = cobra ? feature.toUpperCase() : feature.toLowerCase();
      } else {
        feature = underline ? feature.toUpperCase() : feature.toLowerCase();
      }
      sb.append(feature);
      underline = false;
    }
    return sb.toString();
  }

  private static void checkInputString(String input) {
    if (input.isBlank() || input.startsWith(UNDERLINE)) {
      throw new RuntimeException(String.format("%s不合格式", input));
    }
  }
}

package org.chun.codegen.util;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Scanner;

@Slf4j
public class InputValidatorUtil {

  private static final String PATH_FOLDER_SYMBOL = "/";
  private static final String ID_FOLDER_SYMBOL = ".";


  public static String id2PathConverter(@NonNull String id) {
    if (id.contains(PATH_FOLDER_SYMBOL)) {
      throw new RuntimeException("參數錯誤, 路徑轉換不該含有特殊符號[/]");
    }

    StringBuilder sb = new StringBuilder("/");
    for (char c : id.toCharArray()) {
      sb.append(c == '.' ? PATH_FOLDER_SYMBOL: c);
    }
    return sb.toString();
  }

  public static String path2IdConverter(@NonNull String path) {
    if (path.contains(ID_FOLDER_SYMBOL)) {
      throw new RuntimeException("參數錯誤, 路徑轉換不該含有特殊符號[.]");
    }

    StringBuilder sb = new StringBuilder();
    char[] chars = path.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      if(i==0 && chars[0] =='/'){
        continue;
      }
      sb.append(chars[i]=='/'? ID_FOLDER_SYMBOL : chars[i]);
    }
    return sb.toString();
  }

  public static String inputBool(Scanner scanner) {
    String input;
    do {
      input = scanner.nextLine();
      if (StringUtils.endsWithIgnoreCase("Y", input) || StringUtils.endsWithIgnoreCase("N", input)) {
        break;
      } else {
        log.error("參數錯誤, 不得為 Y,N 以外的參數");
      }
    } while (true);
    return input;
  }
}

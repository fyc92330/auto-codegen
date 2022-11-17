package org.chun.codegen.util;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Scanner;
import java.util.regex.Pattern;

@Slf4j
public class InputValidatorUtil {

  private static final String PATH_FOLDER_SYMBOL = "/";
  private static final String ID_FOLDER_SYMBOL = ".";
  private static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]*");

  public static String id2PathConverter(@NonNull String id) {
    if (id.contains(PATH_FOLDER_SYMBOL)) {
      throw new RuntimeException("參數錯誤, 路徑轉換不該含有特殊符號[/]");
    }

    StringBuilder sb = new StringBuilder("/");
    for (char c : id.toCharArray()) {
      sb.append(c == '.' ? PATH_FOLDER_SYMBOL : c);
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
      if (i == 0 && chars[0] == '/') {
        continue;
      }
      sb.append(chars[i] == '/' ? ID_FOLDER_SYMBOL : chars[i]);
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
        log.error("參數錯誤, 不得為 Y,N 以外的參數. 請重新輸入: ");
      }
    } while (true);
    return input;
  }

  public static String inputId(Scanner scanner) {
    String input;
    do {
      input = scanner.nextLine();
      if (input.contains(PATH_FOLDER_SYMBOL)) {
        log.error("ID不得使用斜線符號[{}], 請使用[{}]", PATH_FOLDER_SYMBOL, ID_FOLDER_SYMBOL);
      } else {
        break;
      }
    } while (true);
    return input;
  }

  public static String inputPath(Scanner scanner) {
    String input;
    do {
      input = scanner.nextLine();
      if (input.contains(ID_FOLDER_SYMBOL)) {
        log.error("路徑不得使用點符號[{}], 請使用[{}]", ID_FOLDER_SYMBOL, PATH_FOLDER_SYMBOL);
      } else {
        break;
      }
    } while (true);
    return input;
  }

  public static String inputHost(Scanner scanner) {
    String input;
    do {
      input = scanner.nextLine();
      boolean isNormalId = input.contains(ID_FOLDER_SYMBOL) && !input.contains(PATH_FOLDER_SYMBOL);
      if (isNormalId || input.equalsIgnoreCase("localhost")) {
        break;
      } else {
        log.error("路徑不得使用點符號[{}], 請使用[{}]", PATH_FOLDER_SYMBOL, ID_FOLDER_SYMBOL);
      }
    } while (true);
    return input;
  }

  public static String inputPort(Scanner scanner) {
    String input;
    do {
      input = scanner.nextLine();
      if(DIGIT_PATTERN.matcher(input).matches()){
        break;
      }else {
        log.error("請輸入正確的port號");
      }
    } while (true);
    return input;
  }
}

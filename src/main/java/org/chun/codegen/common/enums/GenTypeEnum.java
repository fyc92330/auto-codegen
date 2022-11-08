package org.chun.codegen.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenTypeEnum {

  BASE_DAO("BaseDao.java", true),
  DAO("Dao.java", false),
  BASE_VO("BaseVo.java", true),
  VO("Vo.java", false),
  BASE_MAPPER("Base.xml", true),
  MAPPER(".xml", false);

  private final String baseFileName;
  private final boolean overwriteIfExists;

  public boolean overwriteIfExists() {
    return this.overwriteIfExists;
  }
}


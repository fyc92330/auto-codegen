package org.chun.codegen.common.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TablePrimaryKeyConstraint {
  private String tableName;
  private String columnName;
  private String dataType;
  private String constraintName;
  private String constraintType;
  private String columnDefault;
  private String isNullable;
  private String isIdentity;
  private String identityGeneration;
  private String identityStart;
  private String identityIncrement;
  private String identityMaximum;
  private String identityMinimum;
  private String identityCycle;
}

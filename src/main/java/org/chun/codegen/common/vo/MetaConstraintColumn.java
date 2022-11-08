package org.chun.codegen.common.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MetaConstraintColumn {
  private String owner;
  private String constraintName;
  private String tableName;
  private String columnName;
  private Integer position;
}

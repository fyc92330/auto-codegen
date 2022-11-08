package org.chun.codegen.common.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MetaTable {
  private String objectName;
  private String objectType;
  private List<MetaConstraintColumn> primaryKeys;
  private List<MetaTableColumn> columns;
  private List<MetaTableColumn> transientColumns;
}

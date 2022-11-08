package org.chun.codegen.common.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MetaTableColumn {
  private String tableName;
  private String columnName;
  private String dataType;
  private String sqlType;
  private Integer dataLength;
  private String nullable;
  private Integer dataPrecision;
  private Integer dataScale;
  private String columnComment;
  private Boolean autoIncrement = false;
}

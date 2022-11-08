package org.chun.codegen.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum DataTypeEnum {

  BYTE_ARRAY("bytea", byte[].class),
  TIMESTAMP("timestamp without time zone", Timestamp.class),
  REAL("real", BigDecimal.class),
  SMALL_INT("smallint", Integer.class),
  INTEGER("integer", Integer.class),
  BIGINT("bigint", Long.class),
  DOUBLE("double precision", BigDecimal.class),
  LONG("numeric(38,0)", Long.class),
  TEXT("text", String.class),
  CHAR("character", String.class),
  VARCHAR("character varying", String.class);

  private final String sqlDataType;
  private final Class<?> clazz;

  public static Map<String, String> getDataTypeMap() {
    Function<DataTypeEnum, String> convert2SimpleName = e -> e.getClazz().getSimpleName();
    return Arrays.stream(values())
        .collect(Collectors.toMap(DataTypeEnum::getSqlDataType, convert2SimpleName));
  }

}

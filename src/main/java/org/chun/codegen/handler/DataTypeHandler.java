package org.chun.codegen.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.logging.log4j.util.Strings;
import org.chun.codegen.common.enums.DataTypeEnum;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class DataTypeHandler implements TypeHandler<String> {
  private static final Map<String, String> dataTypeMap = DataTypeEnum.getDataTypeMap();
  private static final Pattern DEC_PTN = Pattern.compile("^numeric\\([0-9]+,[0-9]+\\)$");
  private static final Pattern LONG_PTN = Pattern.compile("^numeric\\(38,0\\)$");

  @Override
  public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {

  }

  @Override
  public String getResult(ResultSet rs, String columnName) throws SQLException {
    String dbDataType = rs.getString(columnName);
    String dbColumnName = rs.getString("COL_column_name");
    return this.convertJavaDataType(dbDataType, dbColumnName);
  }

  @Override
  public String getResult(ResultSet rs, int columnIndex) throws SQLException {
    return null;
  }

  @Override
  public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
    return null;
  }

  private String convertJavaDataType(String dbType, String columnName) {
    String javaType = null;
    // numeric(38,0) 為Long, 其他則為BigDecimal
    if (DEC_PTN.matcher(dbType).find()) {
      javaType = LONG_PTN.matcher(dbType).find() ? Long.class.getSimpleName() : BigDecimal.class.getName();
    }
    // 若欄位格式為timestamp without time zone, 結尾不是timestamp則javaType從Timestamp改為String
    if (isTimestampWithoutTimeZoneAndNameIsNotEndsWithTimestamp(dbType, columnName)) {
      javaType = String.class.getSimpleName();
    }
    if (javaType == null) {
      javaType = dataTypeMap.get(dbType) == null ? dbType : dataTypeMap.get(dbType);
    }
    return javaType;
  }

  private boolean isTimestampWithoutTimeZoneAndNameIsNotEndsWithTimestamp(String dbType, String columnName) {
    return DataTypeEnum.TIMESTAMP.getSqlDataType().equals(dbType) &&
        Strings.isNotBlank(columnName)&&
        !StringUtils.endsWithIgnoreCase(columnName, DataTypeEnum.TIMESTAMP.name());
  }
}

package org.chun.codegen.common.dao;

import org.apache.ibatis.annotations.Param;
import org.chun.codegen.common.vo.MetaTable;
import org.chun.codegen.common.vo.TablePrimaryKeyConstraint;

import java.util.List;

public interface MetaDao {

  List<MetaTable> listTableWithColumn(@Param("tableName") String tableName);

  TablePrimaryKeyConstraint getTablePrimaryKey(@Param("tableName") String tableName);

}

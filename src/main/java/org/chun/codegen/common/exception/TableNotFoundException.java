package org.chun.codegen.common.exception;

public class TableNotFoundException extends RuntimeException{

  public TableNotFoundException(String tableName){
    super(String.format("%s不存在.", tableName));
  }
}

package org.chun.codegen.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CodegenEnvironment {
  private String packageVoBase;
  private String packageVoExtend;
  private String packageDaoBase;
  private String packageDaoExtend;
  private String packageMapperBase;
  private String packageMapperExtend;

  private String templateVoBase;
  private String templateVoExtend;
  private String templateDaoBase;
  private String templateDaoExtend;
  private String templateMapperBase;
  private String templateMapperExtend;

  private String outputMainDir;
  private String outputVoPath;
  private String outputDaoPath;
  private String outputMapperPath;

  private Boolean isBuilderMode;
}

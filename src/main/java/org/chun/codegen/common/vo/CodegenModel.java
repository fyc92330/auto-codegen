package org.chun.codegen.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.chun.codegen.common.enums.GenTypeEnum;
import org.chun.codegen.util.JavaCodeUtil;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CodegenModel {
  private String packageName;
  private String voPackageName;
  private String baseVoPackageName;
  private List<String> importList;
  private Date generateTime;
  private MetaTable tableDetail;
  private GenTypeEnum genTypeEnum;

  public CodegenModel(MetaTable table, GenTypeEnum genTypeEnum, CodegenEnvironment env) {
    this.voPackageName = env.getPackageVoExtend();
    this.baseVoPackageName = env.getPackageVoBase();
    this.genTypeEnum = genTypeEnum;
    this.tableDetail = table;
    this.generateTime = Calendar.getInstance().getTime();
    this.packageName = switch (genTypeEnum) {
      case DAO, MAPPER -> env.getPackageDaoExtend();
      case BASE_DAO, BASE_MAPPER -> env.getPackageDaoBase();
      case VO -> env.getPackageVoExtend();
      case BASE_VO -> env.getPackageVoBase();
    };

    this.importList = GenTypeEnum.MAPPER == genTypeEnum || GenTypeEnum.BASE_MAPPER == genTypeEnum
        ? Collections.emptyList()
        : JavaCodeUtil.getJavaImport(table, env, genTypeEnum);
  }
}

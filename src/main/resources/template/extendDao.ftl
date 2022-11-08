<#import "util/codegenUtil.ftl" as codegenUtil>
<#import "util/javaImportUtil.ftl" as javaImportUtil>
package ${packageName};

<#assign imports=javaImportUtil.getImportList(importList, false) />
${imports}
<#assign className=codegenUtil.underscore2Cobra(tableDetail.objectName) />

public interface ${className}Dao extends ${className}BaseDao {
}

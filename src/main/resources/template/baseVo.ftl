<#import "util/codegenUtil.ftl" as codegenUtil>
<#import "util/javaImportUtil.ftl" as javaImportUtil>
package ${packageName};

<#assign imports=javaImportUtil.getImportList(importList, false) />
${imports}

import java.io.Serializable;
<#assign className=codegenUtil.underscore2Cobra(tableDetail.objectName) />

public class ${className}BaseVo extends BaseVo implements Serializable, Cloneable {

    public ${className}BaseVo(){
    }

<#list tableDetail.columns as column>
    <#if column.columnComment??>/** ${column.columnComment} */</#if>
    private ${codegenUtil.replaceNoImportPkg(column.dataType)} ${codegenUtil.underscore2Camel(column.columnName)};

</#list>
<#list tableDetail.columns as column>
    <#assign camelColName=codegenUtil.underscore2Camel(column.columnName)/>
    <#assign cobraColName=codegenUtil.underscore2Cobra(column.columnName)/>
    <#assign javaType=codegenUtil.replaceNoImportPkg(column.dataType)/>
    public ${javaType} get${cobraColName}() {
    return this.${camelColName};
    }

    public void set${cobraColName}( ${javaType} ${camelColName} ) {
    this.${camelColName} = ${camelColName};
    }

</#list>
}
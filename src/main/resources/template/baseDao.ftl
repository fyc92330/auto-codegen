<#import "util/codegenUtil.ftl" as codegenUtil>
<#import "util/javaImportUtil.ftl" as javaImportUtil>
package ${packageName};

<#assign imports=javaImportUtil.getImportList(importList, false) />
${imports}
<#assign className=codegenUtil.underscore2Cobra(tableDetail.objectName) />

public interface ${className}BaseDao {

    List<${className}Vo> query(Object params);

    int insert(Object params);

    List<${className}Vo> listAll();

    Integer count(Object params);

    int delete(Object params);

<#if tableDetail.primaryKeys?size gt 0 >
<#assign paramString=codegenUtil.getPkParamString(tableDetail.primaryKeys, tableDetail.columns)/>
    ${className}Vo getByPk(${paramString});

    int deleteByPk(${paramString});

    int update(Object params);

    int forceUpdate(Object params);
</#if>
}
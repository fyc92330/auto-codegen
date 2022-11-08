<#--<#import "./codegenUtil.ftl" as codegenUtil>-->
<#-- 取得匯入檔案 -->
<#function getImportList list isLombok>
    <#assign rtn = ""/>
    <#list list as obj>
        <#if rtn = "">
            <#assign rtn = "import ${obj}"/>
        <#else >
            <#assign rtn = rtn + "\nimport ${obj}"/>
        </#if>
    </#list>
    <#if isLombok>
        <#assign rtn = rtn + "\nimport lombok.Getter;\nimport lombok.Setter;\nimport lombok.ToString;" />
    </#if>
    <#return rtn />
</#function>

<#--<#list importList as obj>import ${obj};</#list>-->
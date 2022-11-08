<#--underline 轉 cobra-->
<#function underscore2Cobra item>
    <#assign ret=""/>
    <#list item?split("_") as x>
        <#assign ret=ret + (x?lower_case)?cap_first/>
    </#list>
    <#return ret?cap_first/>
</#function>

<#--underline 轉 camel    -->
<#function underscore2Camel item>
    <#assign ret=""/>
    <#list item?split("_") as x>
        <#assign ret=ret + (x?lower_case)?cap_first/>
    </#list>
    <#return ret?uncap_first/>
</#function>

<#--取得pk的型態    -->
<#function getPkType pkCol cols>
    <#assign ret="" />
    <#list cols as col>
        <#if col.columnName==pkCol.columnName>
            <#assign ret=col.dataType />
            <#break />
        </#if>
    </#list>
    <#return ret />
</#function>

<#--取得pk參數名稱    -->
<#function getPkParamString pkCols cols>
    <#assign ret="" />
    <#list pkCols as pkCol>
        <#if ret=="">
            <#assign ret = "@Param( \"" + underscore2Camel(pkCol.columnName) + "\" ) " + getPkType(pkCol, cols) + " " + underscore2Camel(pkCol.columnName) />
        <#else>
            <#assign ret = ret + ", @Param( \"" + underscore2Camel(pkCol.columnName) + "\" ) " + getPkType(pkCol, cols) + " " + underscore2Camel(pkCol.columnName) />
        </#if>
    </#list>
    <#return ret />
</#function>

<#--處理pk型態-->
<#function replaceNoImportPkg item>
    <#assign ret=item pointPos=item?last_index_of(".") len=item?length />
    <#if pointPos gt 0>
        <#assign ret=item[pointPos + 1..len - 1] />
    </#if>
    <#return ret />
</#function>

<#--檢核pk-->
<#function isPk col, pks>
    <#assign result = false/>
    <#list pks as keyCol>
        <#if keyCol.columnName==col.columnName>
            <#assign result=true/>
            <#break>
        </#if>
    </#list>
    <#return result />
</#function>

<#--轉換D19函數-->
<#function wrapSqlDateToStr sqlType src>
    <#if sqlType=="timestamp without time zone" >
        <#if !src?ends_with("timestamp") >
            <#return "D19(" + src + ")" />
        </#if>
    </#if>
    <#return src />
</#function>

<#--轉換時間字串    -->
<#function wrapSqlToDate sqlType columnName src>
    <#if sqlType=="timestamp without time zone" >
        <#if !columnName?ends_with("timestamp") >
            <#return "to_timestamp(" + src + ", 'YYYY-MM-DD HH24:MI:SS')" />
        </#if>
    </#if>
    <#return src />
</#function>

<#--取得欄位代稱-->
<#function getAliasColumnStr cols indentCount>
    <#assign aliasColStr="" />
    <#list cols as col>
        <#if aliasColStr=="">
            <#assign aliasColStr=wrapSqlDateToStr(col.sqlType, "$\{alias}." + col.columnName) + " as $\{prefix}"?left_pad(50 - (wrapSqlDateToStr(col.sqlType, "$\{alias}." + col.columnName)?length)) + col.columnName />
        <#else>
            <#assign aliasColStr=aliasColStr + "," + "\n"?right_pad(indentCount * 4 + 1, " ") + wrapSqlDateToStr(col.sqlType, "$\{alias}." + col.columnName) + " as $\{prefix}"?left_pad(50 - (wrapSqlDateToStr(col.sqlType, "$\{alias}." + col.columnName)?length)) + col.columnName />
        </#if>
    </#list>
    <#return aliasColStr />
</#function>

<#--取得欄位名稱-->
<#function getSetColString pkCols cols indentCount>
    <#assign setColStr="" />
    <#list pkCols as pkCol>
        <#if setColStr=="">
            <#assign setColStr=pkCol.columnName + "=" + wrapSqlToDate(getPkDbType(pkCol, cols), pkCol.columnName, "#{" + codegenUtil.underscore2Camel(pkCol.columnName) + "}") />
        <#else>
            <#assign setColStr=setColStr + "\n"?right_pad(indentCount * 4 + 1, " ") +"AND " + pkCol.columnName + "=" + wrapSqlToDate(getPkDbType(pkCol, cols), pkCol.columnName, "#{" + codegenUtil.underscore2Camel(pkCol.columnName) + "}") />
        </#if>
    </#list>
    <#return setColStr />
</#function>

<#--設定自動產生key-->
<#function generatedKeySetting cols>
    <#assign result = ""/>
    <#list cols as col>
        <#if col.autoIncrement == true>
            <#assign result=" useGeneratedKeys=\"true\" keyColumn=\"" + col.columnName + "\" keyProperty=\"" + underscore2Camel(col.columnName) + "\""/>
            <#break>
        </#if>
    </#list>
    <#return result />
</#function>

<#--取得pk型態-->
<#function getPkDbType pkCol cols>
    <#assign ret="" />
    <#list cols as col>
        <#if col.columnName==pkCol.columnName>
            <#assign ret=col.sqlType />
            <#break />
        </#if>
    </#list>
    <#return ret />
</#function>
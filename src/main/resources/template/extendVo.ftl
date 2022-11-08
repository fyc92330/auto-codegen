<#import "util/codegenUtil.ftl" as codegenUtil>
<#import "util/javaImportUtil.ftl" as javaImportUtil>
package ${packageName};

<#assign imports=javaImportUtil.getImportList(importList, true) />
${imports}

import java.io.Serializable;
<#assign className=codegenUtil.underscore2Cobra(tableDetail.objectName) />

public class ${className}Vo extends ${className}BaseVo implements Serializable, Cloneable {

    public ${className}Vo() {
        super();
    }

}

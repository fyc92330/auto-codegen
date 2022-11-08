<#import "util/codegenUtil.ftl" as codegenUtil>
<#assign className=codegenUtil.underscore2Cobra(tableDetail.objectName) />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${packageName}.${className}Dao">
    <resultMap id="vo" type="${voPackageName}.${className}Vo" extends="${packageName}.base.${className}BaseDao.vo"/>

</mapper>

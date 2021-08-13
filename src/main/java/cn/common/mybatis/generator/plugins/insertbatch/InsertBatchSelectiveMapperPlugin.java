package cn.common.mybatis.generator.plugins.insertbatch;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

/**
 * @program: mybatisGenerator
 * @description: 批量插入
 * @author: yingkb
 **/
public class InsertBatchSelectiveMapperPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        interfaze.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        FullyQualifiedJavaType tList = FullyQualifiedJavaType.getNewListInstance();
        Method method = new Method("insertBatchSelective");
        FullyQualifiedJavaType tSelective = new FullyQualifiedJavaType(introspectedTable.getRules().calculateAllFieldsClass().getShortName()+"."+"Column");
        method.addParameter(new Parameter(tList, "list", "@Param(\"list\")"));
        method.addParameter(new Parameter(tSelective, "selective", "@Param(\"selective\")", true));

        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        interfaze.addMethod(method);
        return true;
    }
}

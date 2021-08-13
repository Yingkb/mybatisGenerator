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
public class InsertBatchMapperPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        interfaze.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        Method method = new Method("insertBatch");
        method.addParameter(new Parameter(
                new FullyQualifiedJavaType("java.util.List<" + introspectedTable.getBaseRecordType() + ">"), "list"));
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        interfaze.addMethod(method);
        return true;
    }
}

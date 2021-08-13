package cn.common.mybatis.generator.plugins.searchbyentity;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

/**
 * @program: mybatisGenerator
 * @description: 在mapper中添加searchByEntity方法
 * @author: yingkb
 **/
public class SearchByEntityMapperPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {

        interfaze.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        Method method = new Method("searchByEntity");
        method.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), "record"));
        method.setReturnType(new FullyQualifiedJavaType("java.util.List<"+introspectedTable.getBaseRecordType()+">"));
        interfaze.addMethod(method);
        return true;
    }
}

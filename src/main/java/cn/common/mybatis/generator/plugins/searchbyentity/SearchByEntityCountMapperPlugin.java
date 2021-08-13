package cn.common.mybatis.generator.plugins.searchbyentity;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

/**
 * @program: mybatisGenerator
 * @description: 在mapper接口中添加searchByEntityCount方法
 * @author: yingkb
 **/
public class SearchByEntityCountMapperPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {

        Method method = new Method("searchByEntityCount");
        method.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), "record"));
        method.setReturnType(new FullyQualifiedJavaType("java.lang.Integer"));
        interfaze.addMethod(method);
        return true;
    }
}

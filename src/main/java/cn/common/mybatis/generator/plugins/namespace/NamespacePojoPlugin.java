package cn.common.mybatis.generator.plugins.namespace;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;

import java.util.List;

/**
 * @program: mybatisGenerator
 * @description: xml namespace 改成扩实体类的名称
 * @author: yingkb
 **/
public class NamespacePojoPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        List<Attribute> ss = document.getRootElement().getAttributes();
        for (Attribute attribute : ss) {
            if("namespace".equals(attribute.getName())){
                ss.remove(attribute);
                break;
            }
        }
        document.getRootElement().addAttribute(new Attribute("namespace", introspectedTable.getBaseRecordType()));
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
}

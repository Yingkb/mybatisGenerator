package cn.common.mybatis.generator.plugins.namespace;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;

import java.util.List;

/**
 * @program: mybatisGenerator
 * @description: xml namespace 改成扩展接口的名称
 * @author: yingkb
 **/
public class NamespaceExtPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        String oldNamespace = null;
        List<Attribute> ss = document.getRootElement().getAttributes();
        for (Attribute attribute : ss) {
            oldNamespace = attribute.getValue();
            if("namespace".equals(attribute.getName())){
                ss.remove(attribute);
                break;
            }
        }
        document.getRootElement().addAttribute(new Attribute("namespace", oldNamespace+"Ext"));
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
}

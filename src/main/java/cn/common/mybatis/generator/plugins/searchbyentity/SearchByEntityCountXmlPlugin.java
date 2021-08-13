package cn.common.mybatis.generator.plugins.searchbyentity;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @program: mybatisGenerator
 * @description: 在xml中添加searchByEntityCount方法
 * @author: yingkb
 **/
public class SearchByEntityCountXmlPlugin extends SearchByEntityabstractXmlPlugin {
    private static final String SEARCHBYENTITYCOUNT = "searchByEntityCount";

    @Override
    protected XmlElement doXml(Document document, IntrospectedTable introspectedTable) {

        String baseRecordType = introspectedTable.getBaseRecordType();
        XmlElement searchByEntityElement = new XmlElement(KEY_SELECT);
        searchByEntityElement.addAttribute(new Attribute("id", SEARCHBYENTITYCOUNT));
        searchByEntityElement.addAttribute(new Attribute("resultType", "java.lang.Integer"));
        searchByEntityElement.addAttribute(new Attribute("parameterType", baseRecordType));
        return searchByEntityElement;
    }

    @Override
    protected String doContent(IntrospectedTable introspectedTable) {
        return super.createWhere(introspectedTable,"count(*)");
    }
}

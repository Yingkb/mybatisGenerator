package cn.common.mybatis.generator.plugins.searchbyentity;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @program: mybatisGenerator
 * @description: 在xml中添加searchByEntity方法
 * @author: yingkb
 **/
public class SearchByEntityXmlPlugin extends SearchByEntityabstractXmlPlugin {
    private static final String SEARCHBYENTITY = "searchByEntity";
    @Override
    protected XmlElement doXml(Document document, IntrospectedTable introspectedTable) {

        String baseResultMapId = introspectedTable.getBaseResultMapId();
        String baseRecordType = introspectedTable.getBaseRecordType();
        XmlElement searchByEntityElement = new XmlElement(KEY_SELECT);
        searchByEntityElement.addAttribute(new Attribute("id", SEARCHBYENTITY));
        searchByEntityElement.addAttribute(new Attribute("resultMap", baseResultMapId));
        searchByEntityElement.addAttribute(new Attribute("parameterType", baseRecordType));
        return searchByEntityElement;
    }

    @Override
    protected String doContent(IntrospectedTable introspectedTable) {
        return super.createWhere(introspectedTable,
                "<include refid=\"" + introspectedTable.getBaseColumnListId() + "\"/>");
    }
}

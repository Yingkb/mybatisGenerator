package cn.common.mybatis.generator.plugins.searchbyentity;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * @program: mybatisGenerator
 * @description: 实例类查询条件生成
 * @author: yingkb
 **/
public abstract class SearchByEntityabstractXmlPlugin extends PluginAdapter {
    protected static final String KEY_SELECT = "select";

    protected static final String KEY_SPACING = " ";
    protected static final String KEY_4_SPACING = KEY_SPACING + KEY_SPACING + KEY_SPACING + KEY_SPACING;
    protected static final String KEY_5_SPACING = KEY_SPACING + KEY_SPACING + KEY_SPACING + KEY_SPACING + KEY_SPACING;
    protected static final String KEY_6_SPACING = KEY_SPACING + KEY_SPACING + KEY_SPACING + KEY_SPACING + KEY_SPACING
            + KEY_SPACING;

    protected static final String KEY_LINE_END = "\n";

    protected static final String KEY_FROM = "from";
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement parentElement = document.getRootElement();
        XmlElement searchByEntityElement = doXml(document, introspectedTable);

        TextElement searchByEntityText = new TextElement(doContent(introspectedTable));
        searchByEntityElement.addElement(searchByEntityText);
        parentElement.addElement(searchByEntityElement);

        return true;
    }

    protected abstract XmlElement doXml(Document document, IntrospectedTable introspectedTable);

    protected abstract String doContent(IntrospectedTable introspectedTable);

    protected String createWhere(IntrospectedTable introspectedTable, String formField) {
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        StringBuilder content = new StringBuilder();
        content.append(KEY_SELECT);
        content.append(KEY_LINE_END);
        content.append(KEY_4_SPACING + formField);
        content.append(KEY_LINE_END);
        content.append(KEY_4_SPACING + KEY_FROM + KEY_SPACING + tableName + KEY_LINE_END);
        content.append(KEY_4_SPACING + "<where>" + KEY_LINE_END);
        List<IntrospectedColumn> introspectedColumnList = introspectedTable.getBaseColumns();
        String javaProperty = null;
        String columnName = null;
        IntrospectedColumn introspectedColumn = null;
        int size = introspectedColumnList.size();
        for (int i = 0; i < size; i++) {
            introspectedColumn = introspectedColumnList.get(i);
            javaProperty = introspectedColumn.getJavaProperty();
            columnName = introspectedColumn.getActualColumnName();
            content.append(KEY_5_SPACING);
            content.append("<if test=\"" + javaProperty + " != null\">");
            content.append(KEY_LINE_END);
            content.append(KEY_6_SPACING);
            if (i != 0) {
                content.append("AND" + KEY_SPACING);
            }
            content.append(
                    columnName + "=" + "#{" + javaProperty + ",jdbcType=" + introspectedColumn.getJdbcTypeName() + "}");
            content.append(KEY_LINE_END);
            content.append(KEY_5_SPACING);
            content.append("</if>");
            content.append(KEY_LINE_END);
        }
        content.append(KEY_4_SPACING + "</where>");
        return content.toString();

    }
}

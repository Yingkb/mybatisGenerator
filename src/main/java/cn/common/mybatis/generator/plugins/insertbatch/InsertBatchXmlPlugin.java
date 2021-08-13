package cn.common.mybatis.generator.plugins.insertbatch;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * @program: mybatisGenerator
 * @description: 批量插入
 * @author: yingkb
 **/
public class InsertBatchXmlPlugin extends PluginAdapter {
    protected static final String KEY_INSERT    = "insert";
    protected static final String KEY_INTO      = "into";
    protected static final String VALUES        = "values";
    protected static final String LEFT_BRACKET  = "(";
    protected static final String RIGHT_BRACKET = ")";
    protected static final String ITEM          = "item";

    protected static final String KEY_SPACING   = " ";
    protected static final String KEY_4_SPACING = KEY_SPACING + KEY_SPACING + KEY_SPACING + KEY_SPACING;
    protected static final String KEY_5_SPACING = KEY_SPACING + KEY_SPACING + KEY_SPACING + KEY_SPACING + KEY_SPACING;
    protected static final String KEY_6_SPACING = KEY_SPACING + KEY_SPACING + KEY_SPACING + KEY_SPACING + KEY_SPACING
            + KEY_SPACING;

    protected static final String KEY_LINE_END  = "\n";

    protected static final String INSERTBATCH   = "insertBatch";

    @Override
    public boolean validate(List<String> arg0) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement parentElement = document.getRootElement();
        XmlElement insertBatchElement = doXml(document, introspectedTable);

        TextElement insertBatchText = new TextElement(createContent(introspectedTable));
        insertBatchElement.addElement(insertBatchText);
        parentElement.addElement(insertBatchElement);

        return true;
    }

    protected XmlElement doXml(Document document, IntrospectedTable introspectedTable) {

        XmlElement searchByEntityElement = new XmlElement(KEY_INSERT);
        searchByEntityElement.addAttribute(new Attribute("id", INSERTBATCH));
        searchByEntityElement.addAttribute(new Attribute("parameterType", "java.util.List"));
        return searchByEntityElement;
    }

    protected String createContent(IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        StringBuilder content = new StringBuilder();
        content.append("<selectKey resultType=\"int\" keyProperty=\"id\" order=\"AFTER\">");
        content.append(KEY_LINE_END);
        content.append(KEY_6_SPACING + "SELECT");
        content.append(KEY_LINE_END);
        content.append(KEY_6_SPACING + "LAST_INSERT_ID() ");
        content.append(KEY_LINE_END);
        content.append(KEY_4_SPACING);
        content.append("</selectKey>");
        content.append(KEY_LINE_END);
        content.append(KEY_4_SPACING);
        content.append(KEY_INSERT + KEY_SPACING + KEY_INTO);
        content.append(KEY_SPACING);
        content.append(tableName);
        content.append(KEY_LINE_END);
        content.append(KEY_6_SPACING);
        content.append(LEFT_BRACKET);
        List<IntrospectedColumn> introspectedColumnList = introspectedTable.getBaseColumns();
        String javaProperty = null;
        String columnName = null;
        IntrospectedColumn introspectedColumn = null;
        int size = introspectedColumnList.size();
        for (int i = 0; i < size; i++) {
            introspectedColumn = introspectedColumnList.get(i);
            columnName = introspectedColumn.getActualColumnName();
            if (i != 0 && i % 3 == 0) {
                content.append(KEY_6_SPACING);
            }
            content.append(columnName);
            if (i != size - 1) {
                content.append(",");
            }
            if (i % 3 == 2) {
                content.append(KEY_LINE_END);
            }
        }
        content.append(RIGHT_BRACKET);
        content.append(KEY_LINE_END);
        content.append(KEY_4_SPACING);
        content.append(VALUES);
        content.append(KEY_LINE_END);
        content.append(KEY_4_SPACING);
        content.append("<foreach collection=\"list\" item=\"" + ITEM + "\" index=\"index\" separator=\",\" >");
        content.append(KEY_LINE_END);
        content.append(KEY_6_SPACING);
        content.append(LEFT_BRACKET);
        for (int i = 0; i < size; i++) {
            introspectedColumn = introspectedColumnList.get(i);
            javaProperty = introspectedColumn.getJavaProperty();

            if (i != 0 && i % 3 == 0) {
                content.append(KEY_6_SPACING);
            }
            content.append(
                    "#{" + ITEM + "." + javaProperty + ",jdbcType=" + introspectedColumn.getJdbcTypeName() + "}");
            if (i != size - 1) {
                content.append(",");
            }
            if (i % 3 == 2) {
                content.append(KEY_LINE_END);
            }
        }
        content.append(RIGHT_BRACKET);
        content.append(KEY_LINE_END);
        content.append(KEY_4_SPACING);
        content.append("</foreach>");

        return content.toString();

    }
}

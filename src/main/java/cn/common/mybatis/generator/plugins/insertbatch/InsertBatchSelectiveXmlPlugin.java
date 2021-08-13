package cn.common.mybatis.generator.plugins.insertbatch;

import cn.common.mybatis.generator.plugins.util.CommTools;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.List;

/**
 * @program: mybatisGenerator
 * @description: TODO
 * @author: yingkb
 **/
public class InsertBatchSelectiveXmlPlugin extends PluginAdapter {
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

    protected static final String INSERTBATCH   = "insertBatchSelective";
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement parentElement = document.getRootElement();
        XmlElement insertBatchElement = doXml(document, introspectedTable);
        createContent(introspectedTable,insertBatchElement);
        parentElement.addElement(insertBatchElement);
        return true;
    }

    protected XmlElement doXml(Document document, IntrospectedTable introspectedTable) {
        XmlElement searchByEntityElement = new XmlElement(KEY_INSERT);
        searchByEntityElement.addAttribute(new Attribute("id", INSERTBATCH));
        searchByEntityElement.addAttribute(new Attribute("parameterType", "map"));
        return searchByEntityElement;
    }

    protected void createContent(IntrospectedTable introspectedTable,XmlElement element) {
        // 使用JDBC的getGenereatedKeys方法获取主键并赋值到keyProperty设置的领域模型属性中。所以只支持MYSQL和SQLServer
        CommTools.useGeneratedKeys(element, introspectedTable);

        element.addElement(new TextElement("insert into "+introspectedTable.getFullyQualifiedTableNameAtRuntime()+" ("));

        XmlElement foreachInsertColumns = new XmlElement("foreach");
        foreachInsertColumns.addAttribute(new Attribute("collection", "selective"));
        foreachInsertColumns.addAttribute(new Attribute("item", "column"));
        foreachInsertColumns.addAttribute(new Attribute("separator", ","));
        foreachInsertColumns.addElement(new TextElement("${column.value}"));

        element.addElement(foreachInsertColumns);

        element.addElement(new TextElement(")"));

        // values
        element.addElement(new TextElement("values"));

        // foreach values
        XmlElement foreachValues = new XmlElement("foreach");
        foreachValues.addAttribute(new Attribute("collection", "list"));
        foreachValues.addAttribute(new Attribute("item", "item"));
        foreachValues.addAttribute(new Attribute("separator", ","));

        foreachValues.addElement(new TextElement("("));

        // foreach 所有插入的列，比较是否存在
        XmlElement foreachInsertColumnsCheck = new XmlElement("foreach");
        foreachInsertColumnsCheck.addAttribute(new Attribute("collection", "selective"));
        foreachInsertColumnsCheck.addAttribute(new Attribute("item", "column"));
        foreachInsertColumnsCheck.addAttribute(new Attribute("separator", ","));

        // 所有表字段
        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns());
        for (int i = 0; i < columns.size(); i++) {
            IntrospectedColumn introspectedColumn = columns.get(i);
            XmlElement check = new XmlElement("if");
            check.addAttribute(new Attribute("test", "'"+introspectedColumn.getActualColumnName()+"' == column.value"));
            check.addElement(new TextElement(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "item.")));

            foreachInsertColumnsCheck.addElement(check);
        }
        foreachValues.addElement(foreachInsertColumnsCheck);

        foreachValues.addElement(new TextElement(")"));
        element.addElement(foreachValues);
    }
}

package cn.common.mybatis.generator.plugins.mapper;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: mybatisGenerator
 * @description: 生成扩展xml
 * @author: yingkb
 **/
public class MapperXmlExtPlugin extends PluginAdapter {
    private ShellCallback shellCallback;
    String namespace ;

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    public MapperXmlExtPlugin() {
        shellCallback = new DefaultShellCallback(false);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        List<Attribute> ss = document.getRootElement().getAttributes();
        for (Attribute attribute : ss) {
            if("namespace".equals(attribute.getName())){
                namespace = attribute.getValue();
                break;
            }
        }
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
        List<GeneratedXmlFile> xmlFiles = introspectedTable.getGeneratedXmlFiles();
        List<GeneratedXmlFile> newXmlFiles = new ArrayList<GeneratedXmlFile>();
        try {
            for (GeneratedXmlFile gxf : xmlFiles) {
                File directory = shellCallback.getDirectory(gxf.getTargetProject(), gxf.getTargetPackage());
                String extFileName = gxf.getFileName().replace(".xml", "") + "Ext.xml";
                File targetFile = new File(directory, extFileName);
                if(!targetFile.exists()){
                    Document document = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
                            XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
                    XmlElement answer = new XmlElement("mapper");


                    answer.addAttribute(new Attribute("namespace", namespace));
                    document.setRootElement(answer);
                    GeneratedXmlFile newXmlFile = new GeneratedXmlFile(document, extFileName, gxf.getTargetPackage(),
                            gxf.getTargetProject(), false, context.getXmlFormatter());
                    newXmlFiles.add(newXmlFile);
                }
            }
        } catch (Exception e) {
        }

        return newXmlFiles;

    }
}

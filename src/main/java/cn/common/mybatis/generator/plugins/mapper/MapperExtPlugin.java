package cn.common.mybatis.generator.plugins.mapper;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: mybatisGenerator
 * @description: 生成扩展接口
 * @author: yingkb
 **/
public class MapperExtPlugin extends PluginAdapter {
    private ShellCallback shellCallback;

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    public MapperExtPlugin(){
        shellCallback = new DefaultShellCallback(false);
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {

        List<GeneratedJavaFile> javaFiles = introspectedTable.getGeneratedJavaFiles();
        List<GeneratedJavaFile> newJavaFiles = new ArrayList<GeneratedJavaFile>();
        try {
            String javaFileName = null;
            for (GeneratedJavaFile g : javaFiles) {
                javaFileName = g.getFileName().replace(".java", "");
                if (javaFileName.endsWith("Mapper")) {
                    Interface unit1 = new Interface(g.getTargetPackage() + "." + javaFileName + "Ext");
                    unit1.addSuperInterface(g.getCompilationUnit().getType());

                    unit1.setVisibility(JavaVisibility.PUBLIC);
                    GeneratedJavaFile newJavaFile = new GeneratedJavaFile(unit1, g.getTargetProject(),
                            g.getFileEncoding(), introspectedTable.getContext().getJavaFormatter());
                    File directory = shellCallback.getDirectory(newJavaFile.getTargetProject(),newJavaFile.getTargetPackage());
                    File targetFile = new File(directory, newJavaFile.getFileName());
                    if (!targetFile.exists()) {
                        newJavaFiles.add(newJavaFile);
                    }
                }
            }
        } catch (Exception e) {
        }
        return newJavaFiles;

    }
}

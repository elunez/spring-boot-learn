package com.ydyno.util;

import com.ydyno.vo.ColumnInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * 代码生成
 *
 * @author jie
 * @date 2019-01-02
 */
@Slf4j
public class GeneratorUtil {

    private static final String TIMESTAMP = "Timestamp";

    private static final String BIGDECIMAL = "BigDecimal";

    private static final String PK = "PRI";

    private static final String EXTRA = "auto_increment";


    /**
     * 生成代码
     * @param columnInfos
     * @param pack
     * @param author
     * @param tableName
     * @throws IOException
     */
    public static void generatorCode(List<ColumnInfo> columnInfos, String pack, String author, String tableName) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("package", pack);
        map.put("author", author);
        map.put("date", LocalDate.now().toString());
        map.put("tableName", tableName);
        // 转换为小写开头的的类名， hello_world == helloWorld
        String className = ColumnUtil.toCapitalizeCamelCase(tableName);
        // 转换为大写开头的类名, hello_world == HelloWorld
        String changeClassName = ColumnUtil.toCamelCase(tableName);

        map.put("className", className);
        map.put("changeClassName", changeClassName);
        // 是否包含 Timestamp 类型
        map.put("hasTimestamp", false);
        // 是否包含 BigDecimal 类型
        map.put("hasBigDecimal", false);
        // 是否为自增主键
        map.put("auto", false);

        List<Map<String, Object>> columns = new ArrayList<>();
        for (ColumnInfo column : columnInfos) {
            Map<String, Object> listMap = new HashMap<>();
            listMap.put("columnComment", column.getColumnComment());
            listMap.put("columnKey", column.getColumnKey());

            String colType = ColumnUtil.cloToJava(column.getColumnType().toString());
            String changeColumnName = ColumnUtil.toCamelCase(column.getColumnName().toString());
            if (PK.equals(column.getColumnKey())) {
                map.put("pkColumnType", colType);
                map.put("pkChangeColName", changeColumnName);
            }
            if (TIMESTAMP.equals(colType)) {
                map.put("hasTimestamp", true);
            }
            if (BIGDECIMAL.equals(colType)) {
                map.put("hasBigDecimal", true);
            }
            if (EXTRA.equals(column.getExtra())) {
                map.put("auto", true);
            }
            listMap.put("columnType", colType);
            listMap.put("columnName", column.getColumnName());
            listMap.put("isNullable", column.getIsNullable());
            listMap.put("changeColumnName", changeColumnName);
            columns.add(listMap);
        }
        map.put("columns", columns);
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(GeneratorUtil.class, "/template");
        Template template = configuration.getTemplate("Entity.ftl");
        // 获取文件路径
        String filePath = getAdminFilePath(pack, className);
        File file = new File(filePath);
        // 生成代码
        genFile(file, template, map);
    }

    /**
     * 定义文件路径以及名称
     */
    private static String getAdminFilePath(String pack, String className) {
        String ProjectPath = System.getProperty("user.dir") + File.separator;
        String packagePath = ProjectPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        if (!ObjectUtils.isEmpty(pack)) {
            packagePath += pack.replace(".", File.separator) + File.separator;
        }
        return packagePath + "entity" + File.separator + className + ".java";
    }

    private static void genFile(File file, Template template, Map<String, Object> params) throws IOException {
        File parentFile = file.getParentFile();
        // 创建目录
        if (null != parentFile && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        //创建输出流
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        //输出模板和数据模型都对应的文件
        try {
            template.process(params, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}

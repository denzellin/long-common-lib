package com.vivavot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.ITableAnnotationHandler;
import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.baomidou.mybatisplus.generator.config.ITypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.baomidou.mybatisplus.generator.query.SQLQuery;
import com.vivavot.basis.base.BaseListQuery;
import com.vivavot.basis.beans.BaseDTO;
import com.vivavot.basis.persistence.dao.BaseMapperEx;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class GeneratorServiceEntity {

    private INameConvert removeIsPrefixNameConvert() {
        return new INameConvert() {
            @Override
            public String entityNameConvert(TableInfo tableInfo) {
                String camel = underlineToCamel(tableInfo.getName());
                if (camel.isEmpty()) {
                    return camel;
                }
                return Character.toUpperCase(camel.charAt(0)) + camel.substring(1);
            }

            @Override
            public String propertyNameConvert(TableField field) {
                String columnName = field.getColumnName();
                if (columnName != null) {
                    String normalized = columnName.replace("`", "").toLowerCase();
                    if (normalized.startsWith("is_")) {
                        return underlineToCamel(normalized.substring(3));
                    }
                    return underlineToCamel(normalized);
                }
                return field.getPropertyName();
            }
        };
    }

    private String underlineToCamel(String value) {
        StringBuilder builder = new StringBuilder();
        boolean upperNext = false;
        for (char ch : value.toCharArray()) {
            if (ch == '_') {
                upperNext = true;
            } else if (upperNext) {
                builder.append(Character.toUpperCase(ch));
                upperNext = false;
            } else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    @Test
    public void test(){
        /*ClassScaner.scan("com.isylph", ErrorManager.class).forEach(clazz -> {


        });*/
    }

    @Test
    public void encryptCode(){
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("Code2022@Encrypt");
        String code = encryptor.encrypt("abc123;");
        log.info(code);

    }



    @Test
    public void generateCode() {

        String url = "jdbc:mysql://oss.isylph.com:1620/ai_toy?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&nullNamePatternMatchesAll=true&allowPublicKeyRetrieval=true";
        //String url = "jdbc:mysql://localhost:3306/api_log?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&nullNamePatternMatchesAll=true&allowPublicKeyRetrieval=true";
        String username = "sqlai";
        //String username = "root";
        String password = "Sylph@202412";
        //String password = "Sylph123";
        String outDir = "/Volumes/DevOps/code/myBatisGen";
        clearOutputDirectory(outDir);
        String packageName = "com.isylph.ai.persistence";
        List<String> tableNameList = Arrays.asList(
                "llm_rag_knowledge_document"
        );
        ITypeConvert convert = new MySqlTypeConvert();

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("denzel.lin") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .commentDate("yyyy-MM-dd")
                            .dateType(DateType.ONLY_DATE)
                            .outputDir(outDir); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            if (typeCode == Types.TINYINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            if (typeCode == Types.TIMESTAMP) {
                                // 自定义类型转换
                                return DbColumnType.LOCAL_DATE_TIME;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                        .dbQuery(new MySqlQuery())
                        .schema("mybatis-plus")
                        .typeConvert(new MysqlLongConvert())
                        .keyWordsHandler(new MySqlKeyWordsHandler())
                        .databaseQueryClass(SQLQuery.class)
                )
                .packageConfig(builder ->
                        builder.parent(packageName) // 设置父包名
                                .entity("model") // 设置实体类包名
                                .mapper("dao") // 设置 Mapper 接口包名
                                .service("service") // 设置 Service 接口包名
                                .serviceImpl("service.impl") // 设置 Service 实现类包名
                                .xml("mappers") // 设置 Mapper XML 文件包名
                )
                .strategyConfig(builder ->
                                builder.addInclude(tableNameList) // 设置需要生成的表名
                                        .serviceBuilder()
                                        .disableService()
                                        .disableServiceImpl()
                                        .controllerBuilder()
                                        .disable()
                                        .entityBuilder()
                                        .enableFileOverride()
                                        .disableSerialVersionUID()
                                        .nameConvert(removeIsPrefixNameConvert())
                                        .enableChainModel()
                                        .enableLombok()
                                        .enableTableFieldAnnotation()
                                        .enableRemoveIsPrefix()
                                        .naming(NamingStrategy.underline_to_camel)
                                        .columnNaming(NamingStrategy.underline_to_camel)
                                        .idType(IdType.AUTO)
                                        .formatFileName("%sPO")
                                        .mapperBuilder()
                                        .superClass(BaseMapperEx.class)
                                        .enableFileOverride()
                                        .enableBaseResultMap()
                                        .enableBaseColumnList() // 启用 REST 风格
                        //.addTablePrefix("t_", "c_") // 设置过滤表前缀
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();


        ITableAnnotationHandler tableAnnotationHandler = new TableAnnotationHandler();
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("denzel.lin") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .commentDate("yyyy-MM-dd")
                            .dateType(DateType.ONLY_DATE)
                            .outputDir(outDir); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                                    if (typeCode == Types.SMALLINT) {
                                        // 自定义类型转换
                                        return DbColumnType.INTEGER;
                                    }
                                    if (typeCode == Types.TINYINT) {
                                        // 自定义类型转换
                                        return DbColumnType.INTEGER;
                                    }
                                    if (typeCode == Types.TIMESTAMP) {
                                        // 自定义类型转换
                                        return DbColumnType.LOCAL_DATE_TIME;
                                    }
                                    return typeRegistry.getColumnType(metaInfo);
                                })
                                .dbQuery(new MySqlQuery())
                                .schema("mybatis-plus")
                                .typeConvert(new MysqlLongConvert())
                                .keyWordsHandler(new MySqlKeyWordsHandler())
                                .databaseQueryClass(SQLQuery.class)
                )

                .packageConfig(builder ->
                        builder.parent(packageName) // 设置父包名
                                .entity("dto") // 设置实体类包名
                )
                .strategyConfig(builder ->
                                builder.addInclude(tableNameList) // 设置需要生成的表名
                                        .mapperBuilder()
                                        .disableMapper()
                                        .disableMapperXml()
                                        .serviceBuilder()
                                        .disableService()
                                        .disableServiceImpl()
                                        .controllerBuilder()
                                        .disable()
                                        .entityBuilder()
                                        .superClass(BaseDTO.class)
                                        .disableSerialVersionUID()
                                        .nameConvert(removeIsPrefixNameConvert())
                                        .addIgnoreColumns("is_deleted")
                                        .enableChainModel()
                                        .enableFileOverride()
                                        .enableLombok()
                                        .enableRemoveIsPrefix()
                                        .tableFieldAnnotationHandler((a, b)->new ArrayList<>())
                                        .tableAnnotationHandler(tableAnnotationHandler)
                                        .naming(NamingStrategy.underline_to_camel)
                                        .columnNaming(NamingStrategy.underline_to_camel)
                                        .formatFileName("%sDTO") // 启用 REST 风格
                        //.addTablePrefix("t_", "c_") // 设置过滤表前缀
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("denzel.lin") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .commentDate("yyyy-MM-dd")
                            .dateType(DateType.ONLY_DATE)
                            .outputDir(outDir); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                                    if (typeCode == Types.SMALLINT) {
                                        // 自定义类型转换
                                        return DbColumnType.INTEGER;
                                    }
                                    if (typeCode == Types.TINYINT) {
                                        // 自定义类型转换
                                        return DbColumnType.INTEGER;
                                    }
                                    if (typeCode == Types.TIMESTAMP) {
                                        // 自定义类型转换
                                        return DbColumnType.LOCAL_DATE_TIME;
                                    }
                                    return typeRegistry.getColumnType(metaInfo);
                                })
                                .dbQuery(new MySqlQuery())
                                .schema("mybatis-plus")
                                .typeConvert(new MysqlLongConvert())
                                .keyWordsHandler(new MySqlKeyWordsHandler())
                                .databaseQueryClass(SQLQuery.class)
                )

                .packageConfig(builder ->
                        builder.parent(packageName) // 设置父包名
                                .entity("dto") // 设置实体类包名
                )
                .strategyConfig(builder ->
                                builder.addInclude(tableNameList) // 设置需要生成的表名
                                        .mapperBuilder()
                                        .disableMapper()
                                        .disableMapperXml()
                                        .serviceBuilder()
                                        .disableService()
                                        .disableServiceImpl()
                                        .controllerBuilder()
                                        .disable()
                                        .entityBuilder()
                                        .superClass(BaseListQuery.class)
                                        .disableSerialVersionUID()
                                        .nameConvert(removeIsPrefixNameConvert())
                                        .addIgnoreColumns("is_deleted")
                                        .enableChainModel()
                                        .enableFileOverride()
                                        .enableLombok()
                                        .enableRemoveIsPrefix()
                                        .tableFieldAnnotationHandler((a, b)->new ArrayList<>())
                                        .tableAnnotationHandler(tableAnnotationHandler)
                                        .naming(NamingStrategy.underline_to_camel)
                                        .columnNaming(NamingStrategy.underline_to_camel)
                                        .formatFileName("%sQuery") // 启用 REST 风格
                        //.addTablePrefix("t_", "c_") // 设置过滤表前缀
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("denzel.lin") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .commentDate("yyyy-MM-dd")
                            .dateType(DateType.ONLY_DATE)
                            .outputDir(outDir); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                                    if (typeCode == Types.SMALLINT) {
                                        // 自定义类型转换
                                        return DbColumnType.INTEGER;
                                    }
                                    if (typeCode == Types.TINYINT) {
                                        // 自定义类型转换
                                        return DbColumnType.INTEGER;
                                    }
                                    if (typeCode == Types.TIMESTAMP) {
                                        // 自定义类型转换
                                        return DbColumnType.LOCAL_DATE_TIME;
                                    }
                                    return typeRegistry.getColumnType(metaInfo);
                                })
                                .dbQuery(new MySqlQuery())
                                .schema("mybatis-plus")
                                .typeConvert(new MysqlLongConvert())
                                .keyWordsHandler(new MySqlKeyWordsHandler())
                                .databaseQueryClass(SQLQuery.class)
                )

                .packageConfig(builder ->
                        builder.parent(packageName) // 设置父包名
                                .entity("dto") // 设置实体类包名
                )
                .strategyConfig(builder ->
                                builder.addInclude(tableNameList) // 设置需要生成的表名
                                        .mapperBuilder()
                                        .disableMapper()
                                        .disableMapperXml()
                                        .serviceBuilder()
                                        .disableService()
                                        .disableServiceImpl()
                                        .controllerBuilder()
                                        .disable()
                                        .entityBuilder()
                                        .disableSerialVersionUID()
                                        .nameConvert(removeIsPrefixNameConvert())
                                        .addIgnoreColumns("is_deleted")
                                        .enableChainModel()
                                        .enableFileOverride()
                                        .enableLombok()
                                        .enableRemoveIsPrefix()
                                        .tableFieldAnnotationHandler((a, b)->new ArrayList<>())
                                        .tableAnnotationHandler(tableAnnotationHandler)
                                        .naming(NamingStrategy.underline_to_camel)
                                        .columnNaming(NamingStrategy.underline_to_camel)
                                        .formatFileName("%sVO") // 启用 REST 风格
                        //.addTablePrefix("t_", "c_") // 设置过滤表前缀
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }

    public static void clearOutputDirectory(String outDir) {
        Path outputPath = Paths.get(outDir);
        try {
            Files.createDirectories(outputPath);
            List<Path> children;
            try (var pathStream = Files.list(outputPath)) {
                children = pathStream.toList();
            }
            for (Path child : children) {
                deleteRecursively(child);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to clear output directory: " + outDir, exception);
        }
    }

    private static void deleteRecursively(Path path) throws IOException {
        try (var pathStream = Files.walk(path)) {
            for (Path current : pathStream.sorted(Comparator.reverseOrder()).toList()) {
                Files.deleteIfExists(current);
            }
        }
    }
}


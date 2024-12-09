package com.bzkj;

import com.alibaba.druid.sql.visitor.functions.Hex;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.baomidou.mybatisplus.generator.query.SQLQuery;
import com.isylph.basis.beans.BaseDTO;
import com.isylph.basis.persistence.dao.BaseMapperEx;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.jupiter.api.Test;

import java.sql.Types;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class GeneratorServiceEntity {

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

        String url = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&nullNamePatternMatchesAll=true&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "password";
        String outDir = "/code/myBatisGen";

        String packageName = "com.isylph.device.persistence";
        List<String> tableNameList = Arrays.asList("device"/*, ""*/);


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

                                    return typeRegistry.getColumnType(metaInfo);
                                })
                                .dbQuery(new MySqlQuery())
                                .schema("mybatis-plus")
                                .typeConvert(new MySqlTypeConvert())
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
                                        .enableChainModel()
                                        .enableLombok()
                                        .enableTableFieldAnnotation()
                                        .logicDeleteColumnName("is_deleted")
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


        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("denzel.lin") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .commentDate("yyyy-MM-dd")
                            .dateType(DateType.TIME_PACK)
                            .outputDir(outDir); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                                    if (typeCode == Types.SMALLINT) {
                                        // 自定义类型转换
                                        return DbColumnType.INTEGER;
                                    }
                                    return typeRegistry.getColumnType(metaInfo);
                                })
                                .dbQuery(new MySqlQuery())
                                .schema("mybatis-plus")
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
                                        .enableChainModel()
                                        .enableLombok()
                                        .enableRemoveIsPrefix()
                                        .logicDeleteColumnName("is_deleted")
                                        .naming(NamingStrategy.underline_to_camel)
                                        .columnNaming(NamingStrategy.underline_to_camel)
                                        .formatFileName("%sDTO") // 启用 REST 风格
                        //.addTablePrefix("t_", "c_") // 设置过滤表前缀
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }





}


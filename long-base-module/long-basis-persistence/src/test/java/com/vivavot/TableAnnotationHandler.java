package com.vivavot;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.ITableAnnotationHandler;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.model.ClassAnnotationAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @Author Denzel Lin
 * @Date 2026/3/7 13:53
 * @Version 1.0
 */
public class TableAnnotationHandler implements ITableAnnotationHandler {
    @Override
    public List<ClassAnnotationAttributes> handle(TableInfo tableInfo, Entity entity) {
        List<ClassAnnotationAttributes> annotationAttributesList = new ArrayList<>();
        GlobalConfig globalConfig = tableInfo.getGlobalConfig();
        String comment = tableInfo.getComment();
        if (StringUtils.isBlank(comment)) {
            comment = StringPool.EMPTY;
        }
        boolean kotlin = globalConfig.isKotlin();
        if (!kotlin) {
            // 原先kt模板没有处理这些,作为兼容项
            if (entity.isChain() && entity.isLombok()) {
                annotationAttributesList.add(new ClassAnnotationAttributes("@Accessors(chain = true)", "lombok.experimental.Accessors"));
            }
            if (entity.isLombok()) {
                if (entity.isDefaultLombok()) {
                    // 原先lombok默认只有这两个
                    annotationAttributesList.add(new ClassAnnotationAttributes("@Getter", "lombok.Getter"));
                    annotationAttributesList.add(new ClassAnnotationAttributes("@Setter", "lombok.Setter"));
                    if (entity.isToString()) {
                        annotationAttributesList.add(new ClassAnnotationAttributes("@ToString", "lombok.ToString"));
                    }
                }
            }
        }
        if (globalConfig.isSwagger()) {
            //@ApiModel(value = "${entity}对象", description = "${table.comment!}")
            String displayName = String.format("@ApiModel(value = \"%s对象\", description = \"%s\")", tableInfo.getEntityName(), comment);
            annotationAttributesList.add(new ClassAnnotationAttributes(
                    displayName, "io.swagger.annotations.ApiModel", "io.swagger.annotations.ApiModelProperty"));
        }
        if (globalConfig.isSpringdoc()) {
            //@Schema(name = "${entity}", description = "${table.comment!}")
            String displayName = String.format("@Schema(name = \"%s\", description = \"%s\")", tableInfo.getEntityName(), comment);
            annotationAttributesList.add(new ClassAnnotationAttributes(displayName, "io.swagger.v3.oas.annotations.media.Schema"));
        }
        return annotationAttributesList;
    }
}

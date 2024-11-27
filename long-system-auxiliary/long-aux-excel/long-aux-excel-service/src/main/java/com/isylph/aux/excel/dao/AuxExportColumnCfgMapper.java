package com.isylph.aux.excel.dao;

import com.isylph.aux.excel.model.AuxExportColumnCfgPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 文件导出excel配置 Mapper 接口
 * </p>
 *
 * @author denzel.lin
 * @since 2022-02-05
 */
public interface AuxExportColumnCfgMapper extends BaseMapper<AuxExportColumnCfgPO> {

    List<AuxExportColumnCfgPO> getByModule(String module);

}

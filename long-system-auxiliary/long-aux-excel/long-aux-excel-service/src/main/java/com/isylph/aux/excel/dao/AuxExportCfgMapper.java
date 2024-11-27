package com.isylph.aux.excel.dao;

import com.isylph.aux.excel.model.AuxExportCfgPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author denzel.lin
 * @since 2022-02-05
 */
public interface AuxExportCfgMapper extends BaseMapper<AuxExportCfgPO> {

    AuxExportCfgPO getModuleCfg(String module);

    List<Map<String, Object>> selectDataList(String statement);


}

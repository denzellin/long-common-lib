package com.isylph.aux.excel.service;

import com.isylph.aux.excel.model.AuxExportCfgPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author denzel.lin
 * @since 2022-02-05
 */
public interface AuxExportCfgService extends IService<AuxExportCfgPO> {

    AuxExportCfgPO getModuleCfg(String module);

    List<Map<String, Object>> selectDataList(String statement);


}

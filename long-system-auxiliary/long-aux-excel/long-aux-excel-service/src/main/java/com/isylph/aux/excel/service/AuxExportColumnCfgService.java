package com.isylph.aux.excel.service;

import com.isylph.aux.excel.model.AuxExportColumnCfgPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 文件导出excel配置 服务类
 * </p>
 *
 * @author denzel.lin
 * @since 2022-02-05
 */
public interface AuxExportColumnCfgService extends IService<AuxExportColumnCfgPO> {

    List<AuxExportColumnCfgPO> getByModule(String module);

}

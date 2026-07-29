package com.vivavot.aux.excel.service.impl;

import com.vivavot.aux.excel.model.AuxExportColumnCfgPO;
import com.vivavot.aux.excel.dao.AuxExportColumnCfgMapper;
import com.vivavot.aux.excel.service.AuxExportColumnCfgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 文件导出excel配置 服务实现类
 * </p>
 *
 * @author denzel.lin
 * @since 2022-02-05
 */
@Service
public class AuxExportColumnCfgServiceImpl
        extends ServiceImpl<AuxExportColumnCfgMapper, AuxExportColumnCfgPO>
        implements AuxExportColumnCfgService {

    @Override
    public List<AuxExportColumnCfgPO> getByModule(String module) {
        return baseMapper.getByModule(module);
    }
}

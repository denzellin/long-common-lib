package com.isylph.aux.excel.service.impl;

import com.isylph.aux.excel.model.AuxExportCfgPO;
import com.isylph.aux.excel.dao.AuxExportCfgMapper;
import com.isylph.aux.excel.service.AuxExportCfgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author denzel.lin
 * @since 2022-02-05
 */
@Service
public class AuxExportCfgServiceImpl
        extends ServiceImpl<AuxExportCfgMapper, AuxExportCfgPO>
        implements AuxExportCfgService {

    @Override
    public AuxExportCfgPO getModuleCfg(String module) {
        return baseMapper.getModuleCfg(module);
    }

    @Override
    public List<Map<String, Object>> selectDataList(String statement) {
        return baseMapper.selectDataList(statement);
    }
}

package com.isylph.aux.excel.application.impl;

import com.isylph.aux.api.beans.FileExportInfoDTO;
import com.isylph.aux.api.consts.Errors;
import com.isylph.aux.excel.application.ExcelExportApplication;
import com.isylph.aux.excel.model.AuxExportCfgPO;
import com.isylph.aux.excel.model.AuxExportColumnCfgPO;
import com.isylph.aux.excel.service.AuxExportCfgService;
import com.isylph.aux.excel.service.AuxExportColumnCfgService;
import com.isylph.basis.controller.exception.ReturnException;
import com.isylph.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ExcelExportApplicationImpl implements ExcelExportApplication {


    @Autowired
    private AuxExportCfgService auxExportCfgService;

    @Autowired
    private AuxExportColumnCfgService auxExportColumnCfgService;

    @Override
    public FileExportInfoDTO exportInfoToFile(String module, String path){
        return exportInfoToFile(module, path, null);
    }

    @Override
    public FileExportInfoDTO exportInfoToFile(String module, String path, Map<String, String> params) {

        AuxExportCfgPO cfg = auxExportCfgService.getModuleCfg(module);
        if (null == cfg){
            log.warn("no cfg found: {}", module);
            throw new ReturnException(Errors.CFG_INFO_NOT_FOUND);
        }
        if (StringUtils.isEmpty(cfg.getTemplateFile()) || StringUtils.isEmpty(cfg.getSqlStatement())){
            log.warn("cfg content error: {}", cfg);
            throw new ReturnException(Errors.CFG_INFO_INVALID);
        }

        if (params != null){
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String r = cfg.getSqlStatement().replaceAll("\\{"+ entry.getKey() + "}", entry.getValue());
                cfg.setSqlStatement(r);
            }
        }

        List<AuxExportColumnCfgPO> colCfgs = auxExportColumnCfgService.getByModule(module);
        if (CollectionUtils.isEmpty(colCfgs)){
            log.warn("cfg column content error: {}", cfg);
            throw new ReturnException(Errors.CFG_COLUMN_INFO_INVALID);
        }

        try {
            //创建工作薄对象
            XSSFWorkbook workbook=new XSSFWorkbook(new FileInputStream(cfg.getTemplateFile()));

            //创建工作表对象
            XSSFSheet sheet=workbook.getSheet(cfg.getSheetName());

            List<Map<String, Object>> res = auxExportCfgService.selectDataList(cfg.getSqlStatement());
            //创建内容
            int firstLine = Optional.ofNullable(cfg.getFirstLine()).orElse(2);
            for (int i=0; i<res.size(); i++) {
                XSSFRow row = sheet.createRow(i + firstLine);

                Map<String, Object> line = res.get(i);

                for( AuxExportColumnCfgPO col: colCfgs){
                    if (col.getColumn() == null || StringUtils.isEmpty(col.getKey()) ){
                        log.warn("Invalid column number: {}", col);
                        continue;
                    }

                    String val = Optional.ofNullable(line.get(col.getKey())).map( o -> o.toString()).orElse("");
                    row.createCell(col.getColumn()).setCellValue(val);
                }
            }
            FileExportInfoDTO expFile = new FileExportInfoDTO()
                    .setName(cfg.getModule() + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xlsx");

            expFile.setPath(cfg.getOutputPath() + expFile.getName());
            //文档输出
            FileOutputStream out = new FileOutputStream(path + expFile.getPath());
            workbook.write(out);
            out.close();
            return expFile;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ReturnException(Errors.TEMPLATE_ACCESS_FAILURE);
        }

    }
}

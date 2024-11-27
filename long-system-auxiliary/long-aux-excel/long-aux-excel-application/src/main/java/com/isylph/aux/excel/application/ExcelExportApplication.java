package com.isylph.aux.excel.application;

import com.isylph.aux.api.beans.FileExportInfoDTO;

import java.util.Map;

public interface ExcelExportApplication {

    FileExportInfoDTO exportInfoToFile(String module, String path);

    FileExportInfoDTO exportInfoToFile(String module, String path, Map<String, String> params);

}

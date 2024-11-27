package com.isylph.utils.tool;

import com.isylph.utils.StringUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class ExcelFileUtil {
    /*
     * 读出的结果放在List<HashMap<String, String>>结构中
     * 每行构成一个HashMap健值对, 字段名是Key， 值内容是Value
     * 成功返回结果, 失败返回null
     * 如果失败，则结果写入指定的文件中
     * */

	public static List<HashMap<String, String>> parseFile(
	        String fileRealPath,  /*excel源文件名，包含路径*/
	        List<String> head,    /*预期的表头*/
	        String errorFile) {

		File rootFile = new File(fileRealPath);

		InputStream instream = null;

		List<String> parseResult = new ArrayList<>();
		List<HashMap<String, String>> retVals = new ArrayList<>();

		try {

			instream = new FileInputStream(fileRealPath);
			Workbook readxls = Workbook.getWorkbook(instream);

			Sheet readsheet = readxls.getSheet(0);
			int rsColumns = readsheet.getColumns();
			int rsRows = readsheet.getRows();
			int i, j;

			Cell cell = null;
			List<String> fieldList = new ArrayList<>();
			for (i = 0; i < rsColumns; i++) {
				cell = readsheet.getCell(i, 0);
				String content = cell.getContents().trim();
				if (StringUtils.isEmpty(content)) {
				    continue;
				}
				fieldList.add(content);
				log.debug(cell.getContents());
			}


			String sinfo = "";
			for(int k=0;k<head.size();k++){
			    String skey = head.get(k);
			    boolean sifind = false;
				for (i = 0; i < fieldList.size(); i++) {
					if (skey.equals(fieldList.get(i))) {
						sifind = true;
						break;
					}
				}
				if (sifind == false) {
					sinfo = sinfo + "文件中列:[" + skey + "]未找到 " + "<br>";
				}
			}

			if (sinfo.length() > 0) {
				parseResult.add("导入失败");
				parseResult.add(sinfo);
			}else {

				for (i = 1; i < rsRows; i++) {

				    HashMap<String, String> row = new HashMap<>();
					for (j = 0; j < rsColumns; j++) {
						cell = readsheet.getCell(j, i);
						String content = cell.getContents().trim();
		                if (StringUtils.isEmpty(content)) {
		                    continue;
		                }

		                if (j >= fieldList.size()) {
		                    log.warn("Ingnore the value: {}", content);
		                    break;
		                }
						row.put(fieldList.get(j), content);
					}
					if (!row.isEmpty()) {
					    retVals.add(row);
					}

				}

			}
			instream.close();
			readxls.close();
			if(rootFile.exists()){
				rootFile.getAbsoluteFile().delete();
			}
		}catch (Exception e) {
			try {
				instream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			log.error("{}", e);

			if(rootFile.exists()){
				rootFile.getAbsoluteFile().delete();
			}
		}

		if (!parseResult.isEmpty()) {
		    List<List<String>> result = new ArrayList<>();
		    result.add(parseResult);
		    exportToCsvFormatFile(errorFile, result);
		    return null;
		}
		return retVals;
	}

	public static String exportToCsvFormatFile(
	        String fileName,  /*文件名，包含文件路径*/
	        List<List<String>> contentList
	        )
	{
	    if (contentList.isEmpty()) {
	        return null;
	    }

		File file = new File(fileName);
		if(file.exists()){
			file.delete();
		}

		try {
			file.createNewFile();
	        FileOutputStream out=new FileOutputStream(file,false); //如果追加方式用true
	        StringBuilder sb=new StringBuilder();

	        Iterator<List<String>> it = contentList.iterator();
	        while (it.hasNext()) {
                List<String> ss = it.next();
                if (ss.isEmpty()) {
                    continue;
                }

                Iterator<String> its = ss.iterator();
                while (its.hasNext()) {
                    String content = its.next();
                    sb.append(content);
                    sb.append(",");
                }
                sb.append("\n");
            }

	        out.write(sb.toString().getBytes("GBK"));//注意需要转换对应的字符集
	        out.close();

		} catch (Exception e) {
		    e.printStackTrace();
		    return null;
		}
		return fileName;

	}
}

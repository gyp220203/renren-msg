package io.renren.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Service;

import io.renren.entity.MsgEntity;
import io.renren.service.ParseExcelService;

@Service("parseExcelService")
public class ParseExcelServiceImpl implements ParseExcelService {

	@Override
	public ArrayList<MsgEntity> getMsgListFromExcel(File targetFile) throws IOException {
		ArrayList<MsgEntity> list = new ArrayList<MsgEntity>();
		POIFSFileSystem fs = new POIFSFileSystem(targetFile);
		HSSFWorkbook workBook = new HSSFWorkbook(fs);
		try {
			System.out.println("开始解析");
			HSSFSheet sheet = workBook.getSheetAt(0);
			int rows = sheet.getLastRowNum() + 1;
			for (int r = 1; r < rows; r++) {
				HSSFRow row = sheet.getRow(r);
				if (row == null) {
					continue;
				}
				MsgEntity msg = new MsgEntity();
				String number = getStringVal(row.getCell(0));
				if(StringUtils.isEmpty(number)){
					continue;
				}
				msg.setTelNumber(number);
				
				String templateId = getStringVal(row.getCell(1));
				if (!StringUtils.isEmpty(templateId) && StringUtils.isNumeric(templateId)
						&& templateId.indexOf(".") == -1) {
					msg.setTemplateId(Long.parseLong(templateId));
				}
				msg.setContent(getStringVal(row.getCell(2)));
				if (HSSFDateUtil.isCellDateFormatted(row.getCell(3))) {
					msg.setSendTime(row.getCell(3).getDateCellValue());
				}
				list.add(msg);
			}
			
		} finally {
			workBook.close();
		}
		return list;

	}
	private String getStringVal(HSSFCell cell){
		String value="";
		if(cell != null) {
			switch (cell.getCellTypeEnum()) {

				case FORMULA:
					value =  cell.getCellFormula();
					break;

				case NUMERIC:
					value = String.valueOf(new DecimalFormat("#").format(cell.getNumericCellValue()));
					break;

				case STRING:
					value = cell.getStringCellValue();
					break;

				case BLANK:
					value ="";
					break;

				case BOOLEAN:
					value =	String.valueOf(cell.getBooleanCellValue());
					break;

				case ERROR:
					value =	String.valueOf(cell.getErrorCellValue());
					break;

				default:
					value =	String.valueOf(cell.getCellTypeEnum());
			}
			
		}
		return value;
	}

}

package org.cl.common.util;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportTool {

	/**
	 * 标题名称集合
	 */
	private List<String> titleList = null;

	/**
	 * 值集合
	 */
	private List<List> valueList = null;

	private HSSFWorkbook workbook = null;

	private HSSFSheet sheet = null;

	/**
	 * 标题样式
	 */
	private HSSFCellStyle titleStyle = null;

	public ExportTool(List titleList, List valueList) {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet("联系人");
		HSSFFont font1 = workbook.createFont();
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 加粗
		HSSFCellStyle style1 = workbook.createCellStyle();
		style1.setFont(font1);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
	}

	/**
	 * 设置表头
	 * 
	 * @param workbook
	 * @param sheet
	 * @param titleStr
	 */
	public void loadHSSFWorkbook() {
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < titleList.size(); i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(new HSSFRichTextString(titleList.get(i)));
			cell.setCellStyle(titleStyle);
		}
		for (int i = 0; i < valueList.size(); i++) {
			row = sheet.createRow(i + 1);
			List tempList = valueList.get(i);
			for (int j = 0; j < tempList.size(); j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(new HSSFRichTextString(StrUtils.ObjectToStr(tempList.get(j), "")));
			}
		}
	}
}

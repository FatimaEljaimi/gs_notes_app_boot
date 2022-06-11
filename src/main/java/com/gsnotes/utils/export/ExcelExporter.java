package com.gsnotes.utils.export;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;




public class ExcelExporter extends Excel{

	private String[] columnNames;
	private String[][] data;
	private String sheetName = "";

	public ExcelExporter(String[] columnNames, String[][] data, String sheetName) {
		super();
		this.columnNames = columnNames;
		this.data = data;
		this.sheetName = sheetName;
	}

	@Override
	protected void writeHeader() {
		sheet = workbook.createSheet(sheetName);

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		int i = 0;
		for (String it : columnNames) {
			createCell(row, (i++), it, style);
		}

	}

	@Override
	protected void writeDataLines() {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (int i = 0; i < data.length; i++) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;
			for (int j = 0; j < data[i].length; j++) {
				createCell(row, columnCount++, data[i][j], style);
			}
		}

	}



}





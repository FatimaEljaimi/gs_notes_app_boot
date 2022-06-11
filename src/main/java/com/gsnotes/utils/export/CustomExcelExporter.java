package com.gsnotes.utils.export;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;


public class CustomExcelExporter extends Excel {

    private ExcelFile file;

    public CustomExcelExporter(ExcelFile file) {
        super();
        this.file = file;
    }

    private CellStyle[] getStyles() {

        CellStyle[] styles = new CellStyle[2];
        XSSFFont[] fonts = new XSSFFont[2];

        for (int i = 0; i < 2; i++) {
            fonts[i] = workbook.createFont();
            styles[i] = workbook.createCellStyle();

            fonts[i].setFontHeight(9);
            fonts[i].setFontName("Cambiria");
            if (i == 0) {
                fonts[i].setBold(true);

                //styles[i].setFillForegroundColor(new XSSFColor(new java.awt.Color(225, 238, 217)).getIndex());
                //styles[i].setFillPattern(FillPatternType.SOLID_FOREGROUND);


            }
            styles[i].setWrapText(true);
            styles[i].setBorderTop(BorderStyle.THIN);
            styles[i].setBorderBottom(BorderStyle.THIN);
            styles[i].setBorderRight(BorderStyle.THIN);
            styles[i].setBorderLeft(BorderStyle.THIN);
            styles[i].setAlignment(HorizontalAlignment.CENTER);
            styles[i].setVerticalAlignment(VerticalAlignment.TOP);
            styles[i].setFont(fonts[i]);
        }
        return styles;
    }

    @Override
    protected void writeHeader() {

        String[][] headerData = file.getHeaderData();
        String sheetName = file.getSheetName();

        sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth(15);
        CellStyle[] styles = getStyles();

        for (int i = 0; i < headerData.length; i++) {
            Row row = sheet.createRow(i);
            row.setHeight((short) 800);

            int columnCount = 0;

            for (int column = 0; column < headerData[2].length; column++) {
                if (i == 2) {
                    createCell(row, columnCount++, headerData[i][column], styles[0]);
                } else if (column < headerData[i].length) {
                    createCell(row, columnCount++, headerData[i][column], styles[column % 2]);
                } else
                    createCell(row, columnCount++, "", styles[1]);
            }
        }
    }

    @Override
    protected void writeDataLines() {
        int rowCount = 3;

        String[][] data = file.getData();
        CellStyle style = getStyles()[1];

        for (String[] line : data) {
            int column = 0;
            Row row = sheet.createRow(rowCount++);
            row.setHeight((short) 600);
            while (column < line.length - 2) {
                createCell(row, column, line[column++], style);
            }
            createCellWithFormula(row, column, line[column++], style);
            createCellWithFormula(row, column, line[column], style);
        }
    }

}

package com.gsnotes.utils.export;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public abstract class Excel {
    protected XSSFWorkbook workbook;
    protected XSSFSheet sheet;

    public Excel() {
        workbook = new XSSFWorkbook();
    }

    public XSSFWorkbook getWorkbook() {
        return workbook;
    }


    protected void createCell(Row row, int columnCount, Object value, CellStyle style) {
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    protected void createCellWithFormula(Row row, int columnCount, String formula, CellStyle style) {
        Cell cell = row.createCell(columnCount);
        cell.setCellFormula(formula);
        cell.setCellStyle(style);
    }

    protected abstract void writeHeader();

    protected abstract void writeDataLines();

    public void prepareWorkbook() {
        writeHeader();
        writeDataLines();
    }

    public void export(HttpServletResponse response) throws IOException {
        prepareWorkbook();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }

}





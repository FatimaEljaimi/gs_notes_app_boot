package com.gsnotes.utils.export;

public class ExcelFile {

    private String sheetName;
    private String[][] headerData;
    private String[][] data;

    public ExcelFile() {
    }

    public ExcelFile(String sheetName, String[][] headerData, String[][] data) {
        this.sheetName = sheetName;
        this.headerData = headerData;
        this.data = data;
    }

    public String[][] getHeaderData() {
        return headerData;
    }

    public void setHeaderData(String[][] headerData) {
        this.headerData = headerData;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}

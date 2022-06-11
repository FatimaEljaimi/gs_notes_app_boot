package com.gsnotes.utils.export;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZipExporter {

    private List<XSSFWorkbook> workbooks;
    private List<ExcelFile> files;


    public ZipExporter(List<ExcelFile> files) {
        workbooks = new ArrayList<>();
        this.files = files;
    }

    public void zipFiles(ServletOutputStream responseOutputStream) throws IOException {

        createWorkbooks();

        List<ByteArrayOutputStream> outputStreams = getOutputStreamsFromWorkBooks();

        ZipOutputStream zipFile = new ZipOutputStream(responseOutputStream);

        int i = 0;
        for (ByteArrayOutputStream outputStream : outputStreams) {

            String fileName = files.get(i++).getSheetName();
            ZipEntry zipEntry = new ZipEntry(fileName + ".xlsx");
            zipFile.putNextEntry(zipEntry);

            byte[] bytes = outputStream.toByteArray();
            zipFile.write(bytes, 0, bytes.length);
            outputStream.close();
        }
        zipFile.close();
    }

    public void createWorkbooks() {

        for (ExcelFile file : files) {
            CustomExcelExporter excelExporter = new CustomExcelExporter(file);
            excelExporter.prepareWorkbook();
            workbooks.add(excelExporter.getWorkbook());
        }
    }


    public List<ByteArrayOutputStream> getOutputStreamsFromWorkBooks() throws IOException {
        List<ByteArrayOutputStream> byteStreams = new ArrayList<>();

        for (XSSFWorkbook workbook : workbooks) {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            byteStreams.add(outputStream);
        }

        return byteStreams;

    }

    public void export(HttpServletResponse response) throws IOException {

        ServletOutputStream outputStream = response.getOutputStream();
        zipFiles(outputStream);
        outputStream.close();

    }

}

package com.dabbler.crm.test;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;

public class CreateExcelTest {
    public static void main(String[] args) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("学生");
        cell = row.createCell(1);
        cell.setCellValue("性别");
        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("周嫣");
        cell = row.createCell(1);
        cell.setCellValue("女");

        FileOutputStream fos = new FileOutputStream("D:\\Data File\\xls\\first.xls");
        wb.write(fos);
        fos.close();
        wb.close();
        System.out.println("=============create finish===========");
    }
}

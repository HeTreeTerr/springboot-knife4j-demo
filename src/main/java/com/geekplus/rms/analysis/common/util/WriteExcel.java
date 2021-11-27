package com.geekplus.rms.analysis.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static void main(String[] args) {

        /*Map<String, String> dataMap=new HashMap<String, String>();
        dataMap.put("BankName", "BankName");
        dataMap.put("Addr", "Addr");
        dataMap.put("Phone", "Phone");
        List<Map> list=new ArrayList<Map>();
        list.add(dataMap);
        writeExcel(list, 3, "D:/writeExcel.xlsx");*/

    }

    public static void writeExcel(String[] headers, String[] propertis, List<Map<String, String>> dataList, String finalXlsxPath) {
        OutputStream out = null;
        try {
            Workbook workBook = new XSSFWorkbook();
            // sheet 对应一个工作页
            Sheet sheet = workBook.createSheet();

            //之前添加没有头信息
            Row row1 = sheet.createRow(0);
            for (int m = 0; m < propertis.length; m++) {
                String property = propertis[m];
                Cell cell = row1.createCell(m);
                cell.setCellValue(property);
            }

            /**
             * 往Excel中写新数据
             */
            for (int j = 0; j < dataList.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + 1);
                // 得到要插入的每一条记录
                Map dataMap = dataList.get(j);

                for (int m = 0; m < propertis.length; m++) {
                    String property = propertis[m];
                    String value = dataMap.get(property) == null ? "" : dataMap.get(property).toString();

                    Cell cell = row.createCell(m);
                    cell.setCellValue(value);
                }

            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }

    /**
     * 判断Excel的版本,获取Workbook
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) throws IOException {
        Workbook wb = null;
        if (!file.exists()) {
            new FileOutputStream(file.getAbsoluteFile());
            //file = new File(String.valueOf(file.getAbsoluteFile()));
        }
        FileInputStream in = new FileInputStream(file);
        if (file.getName().endsWith(EXCEL_XLS)) {     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    //数据大时通过分页写入
    public static void writeExcelByPage(String[] headers, String[] propertis, List<Map<String, String>> dataList, String finalXlsxPath) {
        OutputStream out = null;
        try {

            Workbook workBook =  WriteExcel.getWorkbok(new File(finalXlsxPath));

            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);


            /**
             * 往Excel中写新数据
             */
            for (int j = 0; j < dataList.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(sheet.getLastRowNum()+1);
                // 得到要插入的每一条记录
                Map dataMap = dataList.get(j);

                for (int m = 0; m < propertis.length; m++) {
                    String property = propertis[m];
                    String value = dataMap.get(property) == null ? "" : dataMap.get(property).toString();

                    Cell cell = row.createCell(m);
                    cell.setCellValue(value);
                }

            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }

    public static void writeExcelHead(String[] headers, String[] propertis, String finalXlsxPath) {
        OutputStream out = null;
        Workbook workBook = new XSSFWorkbook();
        // sheet 对应一个工作页
        Sheet sheet = workBook.createSheet();

        Row row1 = sheet.createRow(0);
        for (int m = 0; m < propertis.length; m++) {
            String property = propertis[m];
            Cell cell = row1.createCell(m);
            cell.setCellValue(property);
        }
        try {
            out = new FileOutputStream(finalXlsxPath);


            workBook.write(out);
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("head写入成功");
}
}

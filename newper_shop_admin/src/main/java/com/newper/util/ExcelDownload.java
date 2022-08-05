package com.newper.util;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class ExcelDownload {

    /**
     * 엑셀 파일 만들기
     * fileName 파일명, sheet명
     * columns header명, DB column명
     * */
    public static void createExcel(HttpServletResponse response, String fileName, List<String[]> columns, List<Map<String,Object>> data) {
        InputStream is=null;
        SXSSFWorkbook wb=new SXSSFWorkbook(200);
        try{
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Disposition","attachment; fileName=\"" + URLEncoder.encode(fileName+".xlsx", "UTF-8") + "\";");

            SXSSFSheet sh=wb.createSheet(fileName);
            SXSSFRow header=sh.createRow(0);
            XSSFCellStyle headerStyle=(XSSFCellStyle)wb.createCellStyle();
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setFillForegroundColor(new XSSFColor(new Color(226, 239, 218), new DefaultIndexedColorMap()));
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            for(int i=0; i<columns.size();i++) {
                SXSSFCell cell=header.createCell(i);
                String[] head = columns.get(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(head[0]);
            }

            XSSFCellStyle cellStyle=(XSSFCellStyle)wb.createCellStyle();
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);

            XSSFCellStyle moneyStyle = cellStyle.copy();
            moneyStyle.setDataFormat(BuiltinFormats.getBuiltinFormat("#,##0"));

            for(int i=0;i<data.size();i++){
                SXSSFRow srow=sh.createRow(i+1);
                Map<String,Object> map = data.get(i);
                for(int j=0;j<columns.size();j++) {
                    SXSSFCell cell=srow.createCell(j);

                    cell.setCellStyle(cellStyle);
                    String column_name=columns.get(j)[1];
                    String value = map.get(column_name)+"";

                    if(column_name.equalsIgnoreCase("No")) {
                        cell.setCellType(CellType.NUMERIC);
                        cell.setCellValue(i+1);
                    }else {
                        if(value.matches("^\\-?[0-9]{1,9}$")){
                            cell.setCellType(CellType.NUMERIC);

                            if (column_name.contains("TOTAL") || column_name.contains("PRICE") || column_name.contains("POINT")) {
                                cell.setCellStyle(moneyStyle);
                            }

                        }else{
                            cell.setCellType(CellType.STRING);
                        }

                        if(value.equals("null")){
                            cell.setCellValue("");
                        }else {
                            if(value.matches("^\\-?[0-9]{1,9}$")){
                                double numberValue = Double.parseDouble(value);
                                cell.setCellValue(numberValue);
                            }else{
                                cell.setCellValue(value);
                            }
                        }
                    }
                }
            }

            SXSSFRow lastDataRow = sh.getRow(data.size());
            SXSSFRow sumRow = sh.createRow(data.size()+1);
            for(int j=0;j<columns.size();j++) {
                String column_name = columns.get(j)[1];
                if (column_name.contains("TOTAL") || column_name.contains("PRICE") || column_name.contains("POINT")) {
                    SXSSFCell lastCell = lastDataRow.getCell(j);
                    if (lastCell == null) {
                        lastCell = lastDataRow.createCell(j);
                    }
                    CellReference cr = new CellReference(lastCell);
                    String colString = cr.formatAsString();
                    String firstColString = colString.substring(colString.lastIndexOf("!") + 1);
                    String ref = firstColString.charAt(0) + "2:" + firstColString;

                    SXSSFCell sumCell = sumRow.createCell(j);
                    sumCell.setCellFormula("SUM(" + ref + ")");
                    sumCell.setCellStyle(moneyStyle);
                }
            }

            for(int i=0; i<columns.size();i++) {
                try{
                    sh.autoSizeColumn(i);
                    int colWidth=(int)(sh.getColumnWidth(i)*1.5);
                    if(colWidth<2000) {
                        colWidth=2000;
                    }
                    sh.setColumnWidth(i, colWidth);
                }catch (Exception e) {}
            }
            OutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(is!=null)is.close();
                wb.dispose();
                wb.close();
            }catch (Exception e) {}
        }
    }

}

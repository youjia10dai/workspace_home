package main.source.excel; 
 
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
 

/**
 * 利用POI工具创建Excel工作薄和工作表,并向其中写入内容
 * @author la
 *
 */ 
public class CreateExcel { 
     
    private void createExcel()throws IOException { 
        String excelFile="D:\\myexcel.xls"; 
        FileOutputStream fos=new FileOutputStream(excelFile); 
        HSSFWorkbook wb=new HSSFWorkbook();//创建工作薄
        HSSFSheet sheet=wb.createSheet();//创建工作表
        wb.setSheetName(0, "sheet0");//设置工作表名 
         
        HSSFRow row=null; 
        HSSFCell cell=null; 
        for (int i = 0; i < 10; i++) { 
            row=sheet.createRow(i);//新增一行 
            cell=row.createCell(0);//新增一列  0
            cell.setCellValue(i);//向单元格中写入数据 
            cell=row.createCell(1); 
            cell.setCellValue("第"+i+"行"); 
        } 
        wb.write(fos); 
        fos.close(); 
    } 
    /**
     * @param args
     *2012-10-23
     *void
     * @throws IOException 
     */ 
    public static void main(String[] args) throws IOException { 
        new CreateExcel().createExcel(); 
 
    } 
 
} 
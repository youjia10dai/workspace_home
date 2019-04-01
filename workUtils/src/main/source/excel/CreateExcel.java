package main.source.excel; 
 
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
 

/**
 * ����POI���ߴ���Excel�������͹�����,��������д������
 * @author la
 *
 */ 
public class CreateExcel { 
     
    private void createExcel()throws IOException { 
        String excelFile="D:\\myexcel.xls"; 
        FileOutputStream fos=new FileOutputStream(excelFile); 
        HSSFWorkbook wb=new HSSFWorkbook();//����������
        HSSFSheet sheet=wb.createSheet();//����������
        wb.setSheetName(0, "sheet0");//���ù������� 
         
        HSSFRow row=null; 
        HSSFCell cell=null; 
        for (int i = 0; i < 10; i++) { 
            row=sheet.createRow(i);//����һ�� 
            cell=row.createCell(0);//����һ��  0
            cell.setCellValue(i);//��Ԫ����д������ 
            cell=row.createCell(1); 
            cell.setCellValue("��"+i+"��"); 
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
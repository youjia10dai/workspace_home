package main.source.excel;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
 
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
 
/** 
 * @description 修改excel文件
 * @author 陈吕奖
 * @date 2018-07-24 
 */  
public class ChangeCell {
 
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        String fileToBeRead = "D:\\test.xls"; // excel位置
        int coloum = 0; // 比如你要获取第1列
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
                    fileToBeRead));
            HSSFSheet sheet = workbook.getSheetAt(0);
 
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow((short) i);
                if (null == row) {
                    continue;
                } else {
                    HSSFCell cell = row.getCell((short) coloum);
                    if (null == cell) {
                        continue;
                    } else {
                        System.out.println(cell.getStringCellValue());
                        int temp = Integer.parseInt(cell.getStringCellValue());
//                        temp = (int)cell.getNumericCellValue();
                        cell.setCellValue(temp + 1);
                    }
                }
            }
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(fileToBeRead);
                workbook.write(out);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
 
}
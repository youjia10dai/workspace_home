package main.old.helper.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.utils.common.StringUtils;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/** 
 * @description ����Excel�İ�����(��֮ǰ������ĺϼ�,֧�ֶ�ȡ�����޸�)
 * @author ������
 * @date 2018-08-03 
 */

@Component("excelHelper")//ExcelHelper  excelHelper������Զ���ӵ�����
@Scope("prototype") //����   ��Ϊ�������������������
public class ExcelHelper {
    public final Logger log = Logger.getLogger(this.getClass());
    
    /**
     * @fields title ���ڴ洢��ͷ��Ϣ(ֻ֧��һ��һ��) ����:name (�����и��titleName��titleColumn)
     */
    public String[] title;

    /** 
     * @fields input �ļ���,���ڹر���Դ(ֻ��ȡ�ļ�ʱ,ʹ������ͷ���Դ)
     */ 
    FileInputStream input;
    
    /**
     * @fields titleName �洢���е��ֶ���ʾ��
     */
    public String[] titleName;

    /**
     * @fields titleColumn �洢���е��ֶ���(����)
     */
    public String[] titleColumn;

    /**
     * @fields wb ��ʾһ��xls�ļ�
     */
    public HSSFWorkbook wb;

    /**
     * @fields sheet ��ʾһ��������
     */
    public HSSFSheet sheet;

    private ExcelStyleHelper style;
    
    /**
     * @fields file �������ɵ��ļ�����,����excel�ļ�ʱ,��Ҫʹ���������
     */
    public File file;

    /** 
     * @fields rows ��������(��һ�����ݵ�����,һ������µ�һ�е�����������������)
     * �������õ�,����ʱû���õ�(�����ж�����)
     */ 
    private int rows;
    
    /** 
     * @fields cols ��������(��һ�����ݵ�����,һ������µ�һ�е�����������������)
     * �����ж�����
     */ 
    private int cols;
    
    /** 
     * @fields isWrite �����ж��ǲ��Ǵ���һ��excel,Ĭ��Ϊ0(���Ǵ���excel) 1 Ϊд����
     * ͨ�����췽��������
     */ 
    public int isWrite = 0;
    
    /* һ���������췽����һ���Ƕ�ȡ���޸ĵ�,���������Ǵ���excel�� */
    /**
     * @description ���췽��(Ĭ���������ʹ�õ�һ��sheet)
     * @param file
     * @throws Exception
     */
    public ExcelHelper(File file) throws Exception{
        init(file,null);
        log.debug(rows+ "  "+ cols);
    }
    
    /**
     * @description ��ʼ��
     * @param wb   excel����
     * @param sheetName  ����������
     * @param title  excel��һ�б���
     */
    public ExcelHelper(File file, String sheetName, String[] title){
        this.title = title;
        this.isWrite = 1;
        init(file,sheetName);
        //��ʼ��������������
        writeRow(0,titleName);
    }
    
    public ExcelHelper(File file, String[] title){
        this.title = title;
        this.isWrite = 1;
        log.debug(isWrite);
        init(file,"test");
        //��ʼ��������������
        writeRow(0,titleName);
    }

    /**
     * @description Ĭ�ϵĹ��췽��,ֻ��Ϊ�˼̳�ʹ�õ�(ExcelStyleHelper������̳�)
     */
    public ExcelHelper(){}
    
    /* �����ķ���  */
    /** 
     * @description ��ʼ����������������()
     * @author ������ 2018-08-01
     */ 
    public void initRowCol(){
        int temp = 0;
        cols = getRow(0).getLastCellNum();//�±��1��ʼ
        rows = sheet.getLastRowNum();//�±��0��ʼ
        //���rows > 0,��ʾ����������,��ȡ����������������
        if(rows > 0){
            for(int i = 0; i < rows + 1; i++) {
                temp = getRow(i).getLastCellNum();
                if(cols < temp) {
                    cols = temp;
                }
            }
        }
        if(rows == 0 && cols != -1){
            rows = 1;
        }else{
            if(rows != 0){
                rows++;
            }
        }
        if(cols == -1){
            cols = 0;
        }
    }
    
    public void init(File file, String sheetName){
        this.file=file;
        //��ʼ��excel����(���ַ�ʽ)
        log.debug(isWrite);
        if(isWrite==1) {
            if(!file.exists()){
                try {
                    file.createNewFile();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            wb = new HSSFWorkbook();
            sheet = wb.createSheet();
        } else {
            try {
                input = new FileInputStream(file);
                wb = new HSSFWorkbook(input);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            sheet = wb.getSheetAt(0);
            initRowCol();
        }
        //���ù�����������
        if(sheetName != null) {
            wb.setSheetName(0, sheetName);
        }
        //���������ָ������(һ���Ǳ���,һ�����ֶ�)
        /*
         * ���������Ϊ�˻�ȡ�����е�����,�����з��ص���һ��Map
         * ������Ҫͨ���ֶ���ȥ��ȡ����
         */
        if(title != null) {
            //����Ǵ�����Ļ�,����Ͳ����:����������ȥ�ָ�
            if(title[0].contains(":")){
                titleName = new String[title.length];
                titleColumn = new String[title.length];
                StringUtils.splitString(title, titleName, titleColumn);    
            }else{
                titleName = title;
            }
        }
    }
    
    /**
     * @description ��List<Map> ת���� List<String[]>
     * @author ������ 2018-07-24
     * @param oldList
     * @return
     */
    public List<String[]> convert(List<Map<String, Object>> oldList) {
        List<String[]> newList = new ArrayList<String[]>();
        for(Map<String, Object> map : oldList) {
            String[] tmp = new String[titleColumn.length];
            for(int i = 0; i < tmp.length; i++) {
                tmp[i] = map.get(titleColumn[i]).toString();
            }
            newList.add(tmp);
        }
        return newList;
    }
    
    /**
     * @description ��wb����������ļ�(ע����ʽ)
     * ִ�������������ͷ���Դ
     * @author ������ 2018-07-24
     * @throws IOException
     */
    public void write() throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        if(style!=null){
            style.setStyle(wb, sheet);
        }
        wb.write(fos);
        fos.close();
    }
    
    /** 
     * @description �ر�wb��Դ(��ȡ���ļ���ر���Դ)
     * ��pio��wb��û�йرյķ���
     * @author ������ 2018-08-01
     */ 
    public void close(){
        if(wb != null && input != null)
        {
            try {
                input.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /** 
     * @description ��ȡһ��Cell,���Ϊ�վʹ���һ��
     * �ṩx,y��ȡһ��cell
     * @author ������ 2018-07-25
     * @param rowI
     * @param colI
     * @return
     */ 
    public HSSFCell getCell(int rowI, int colI){
        HSSFRow row = sheet.getRow(rowI);
        if(row==null){
            row = sheet.createRow(rowI);
        }
        return getCell(row, colI);
    }
    
    /** 
     * @description ��ȡһ��Cell,���Ϊ�վʹ���һ��
     * �ṩһ��row����ȡ�����row
     * @author ������ 2018-07-25
     * @param row
     * @param colI
     * @return
     */ 
    public HSSFCell getCell(HSSFRow row, int colI){
        short col = (short)colI;
        HSSFCell cell = row.getCell(col);
        if(cell==null){
            cell = row.createCell(col);
        }
        return cell;
    }
    
    /** 
     * @description ��ȡһ��,���Ϊ�վʹ���һ��
     * @author ������ 2018-07-26
     * @param rowI
     * @return
     */ 
    public HSSFRow getRow(int rowI){
        HSSFRow row = sheet.getRow(rowI);
        if(row==null){
            row = sheet.createRow(rowI);
        }
        return row;
    }
    
    /** 
     * @description ��ȡһ���ڵ����е�Ԫ��
     * @author ������ 2018-08-01
     * @param row
     * @return
     */ 
    public HSSFCell[] getCellsByRow(HSSFRow row){
        //������������ȡÿһ������
        HSSFCell[] cells = new HSSFCell[cols];
        for(int i = 0; i < cols; i++) {
            cells[i] = getCell(row, i);
        }
        return cells;
    }
    
    /** 
     * @description ��ȡһ���ڵ����е�Ԫ��
     * @author ������ 2018-08-01
     * @param row
     * @return
     */ 
    public HSSFCell[] getCellsByRow(int rowI){
        HSSFRow row = sheet.getRow(rowI);
        if(row==null){
            row = sheet.createRow(rowI);
        }
        return getCellsByRow(row);
    }
    
    /** 
     * @description ��ȡһ���ڵ����е�Ԫ��
     * @author ������ 2018-08-01
     * @param row
     * @return
     */ 
    public HSSFCell[] getCellsByCol(int colI){
        //��ȡ���е���
        HSSFCell[] cells = new HSSFCell[rows];
        for(int i = 0; i < rows; i++ ){
            HSSFRow row = sheet.getRow(i);
            //��ȡ���е�������
            cells[i] = getCell(row, colI);
        }
        return cells;
    }
    
    /** 
     * @description ����(��ȡ��һ�����ݸ���Ӧ�ú͵�һ����ͬ,
     *              ���Ϊ��,��null���""�Ա����)
     * @author ������ 2018-06-27
     * @param cells
     */ 
    private void complement(String[] cells)
    {
        for(String string : cells) {
            if(string==null)
            {
                string = "";
            }
        }
    }
    
    /** 
     * @description ��һ�鵥Ԫ��ת����Ϊ�ַ�������
     * @author ������ 2018-06-27
     * @param cells
     * @return
     */ 
    private String[] getCellsContext(HSSFCell[] cells)
    {
        String[] contexts = new String[cells.length];
        for(int i = 0; i < cells.length; i++) {
            if(cells[i].getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
                contexts[i] = (new Double(cells[i].getNumericCellValue())).longValue()+"";
            }else{
                contexts[i] = cells[i].getStringCellValue().trim();
            }
        }
        complement(contexts);
//        for(int i = 0; i < contexts.length; i++) {
//            System.out.print(contexts[i]+" ");
//        }
//        System.out.println();
        return contexts;
    }
    
    /** 
     * @description (����޸��޸�sheet��ʽ,���������������)
     * @author ������ 2018-07-26
     * @param style
     */ 
    public void setExcelStyleHelper(ExcelStyleHelper style) {
        this.style = style;
    }
    
    /*   excel��ȡ�ķ���   */
    /** 
     * @description ��ȡָ��һ�е�����
     * @author ������ 2018-06-27
     * @param index
     * @return
     */ 
    public String[] getRowContextByIndex(int index){
        HSSFCell[] cells = getCellsByRow(index);
        return getCellsContext(cells);
    }
    
    /** 
     * @description ��ȡ����������������Χ�ڵ����ݻ�ȡ
     * @author ������ 2018-06-06
     * @param currentheet
     * @param range  lengthΪ0,��ʾ��ȡȫ�������� 
     *               lengthΪ1,��ʾ��ָ��λ�ÿ�ʼ��ȡȫ��������
     *               lengthΪ2,��ʾһ����Χ,����ͷ����β
     * @return
     */
    public List<String[]> getRowByRange(int ... range){
        List<String[]> list = new ArrayList<String[]>();
        if(range.length == 0){
            //��ȡ���ű������
            for(int i = 0; i < rows; i++){
                list.add(getRowContextByIndex(i));
            }
        }else if(range.length == 1){
            for(int i = range[0]; i < rows; i++){
                list.add(getRowContextByIndex(i));
            }
        }else{
            /* �����ʼ�ͽ�����ͬ,��ʾֻ¼��һ����¼ */
            if(range[0] == range[1]){
                list.add(getRowContextByIndex(range[0]));
                return list;
            }
            for(int i = range[0]; i <= range[1]; i++){
                list.add(getRowContextByIndex(i));
            }
        }
        return list;
    }
    
    /** 
     * @description ��ȡָ��һ�е�����
     * @author ������ 2018-06-27
     * @param index
     * @return
     */ 
    public String[] getColContextByIndex(int index){
        HSSFCell[] cells = getCellsByCol(index);
        return getCellsContext(cells);
    }
    
    /*   �޸�excel����   */
    /** 
     * @description �޸ĵ�����Ԫ������
     * @author ������ 2018-07-24
     * @param row
     * @param col
     * @param count
     */ 
    public void modifyCell(int row, int col, String context){
        HSSFCell cell = getCell(row,col);
        cell.setCellValue(context);
    }
    
    /** 
     * @description �޸�һ�е�����
     * @author ������ 2018-07-24
     * @param row
     * @param context
     */ 
    public void modifyRow(int row, String[] context){
        //��ȡ��Ч�ĵ�Ԫ�����
        short cols = (short)context.length;
        for(int i = 0; i < cols; i++) {
            HSSFCell cell = getCell(row,i);
            cell.setCellValue(context[i]);
        }
    }

    /** 
     * @description �޸�һ�е�����(��ָ�����п�ʼ)
     * @author ������ 2018-07-24
     * @param row
     * @param col
     * @param context
     */ 
    public void modifyRow(int row, int col, String[] context){
        //��ȡ��Ч�ĵ�Ԫ�����
        short cols = (short)context.length;
        for(int i = 0; i < cols; i++) {
            HSSFCell cell = getCell(row,col++);
            cell.setCellValue(context[i]);
        }
    }

    /*   ����excel����    */
    /** 
     * @description ����excel�ļ�()
     * @author ������ 2018-07-24
     * @param list
     * @throws IOException
     */ 
    public void createExcel(List<String[]> list) throws IOException{
        // i:Ϊ�ڼ���  j:Ϊ�ڼ���
        for(int i = 0; i < list.size(); i++) {
            String[] strings = list.get(i);
            //������1,������ռһ��
            writeRow(i+1, strings);
        }
        write();
    }

    /**
     * @description дһ������
     * @author ������ 2018-07-24
     * @param rowNum
     * @param data
     */
    public void writeRow(int rowNum, String[] data){
        HSSFRow row=null;
        HSSFCell cell=null;
        row=sheet.createRow(rowNum);//����һ��
        for(int j = 0; j < data.length; j++) {
            cell=row.createCell((short)j);//����һ��  0
            cell.setCellValue(data[j]);//��Ԫ����д������
//            log.debug(cell.getStringCellValue());
        }
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }
}
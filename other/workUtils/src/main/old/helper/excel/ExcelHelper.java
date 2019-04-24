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
 * @description 操作Excel的帮助类(是之前工具类的合集,支持读取并且修改)
 * @author 陈吕奖
 * @date 2018-08-03 
 */

@Component("excelHelper")//ExcelHelper  excelHelper这个是自动添加的名字
@Scope("prototype") //多例   因为这个帮助类自身有属性
public class ExcelHelper {
    public final Logger log = Logger.getLogger(this.getClass());
    
    /**
     * @fields title 用于存储表头信息(只支持一行一列) 名字:name (最终切割成titleName和titleColumn)
     */
    public String[] title;

    /** 
     * @fields input 文件流,用于关闭资源(只读取文件时,使用这个释放资源)
     */ 
    FileInputStream input;
    
    /**
     * @fields titleName 存储所有的字段显示名
     */
    public String[] titleName;

    /**
     * @fields titleColumn 存储所有的字段名(列明)
     */
    public String[] titleColumn;

    /**
     * @fields wb 表示一个xls文件
     */
    public HSSFWorkbook wb;

    /**
     * @fields sheet 表示一个工作簿
     */
    public HSSFSheet sheet;

    private ExcelStyleHelper style;
    
    /**
     * @fields file 最终生成的文件对象,创建excel文件时,需要使用这个对象
     */
    public File file;

    /** 
     * @fields rows 最大的行数(第一列数据的行数,一般情况下第一列的行数就是最大的行数)
     * 列是有用的,行暂时没有用到(数据有多少条)
     */ 
    private int rows;
    
    /** 
     * @fields cols 最大的列数(第一行数据的列数,一般情况下第一行的列数就是最大的列数)
     * 数据有多少列
     */ 
    private int cols;
    
    /** 
     * @fields isWrite 用于判断是不是创建一个excel,默认为0(不是创建excel) 1 为写操作
     * 通过构造方法来控制
     */ 
    public int isWrite = 0;
    
    /* 一共三个构造方法第一个是读取和修改的,后面两个是创建excel的 */
    /**
     * @description 构造方法(默认情况下是使用第一个sheet)
     * @param file
     * @throws Exception
     */
    public ExcelHelper(File file) throws Exception{
        init(file,null);
        log.debug(rows+ "  "+ cols);
    }
    
    /**
     * @description 初始化
     * @param wb   excel对象
     * @param sheetName  工作簿名字
     * @param title  excel第一行标题
     */
    public ExcelHelper(File file, String sheetName, String[] title){
        this.title = title;
        this.isWrite = 1;
        init(file,sheetName);
        //初始化工作簿标题栏
        writeRow(0,titleName);
    }
    
    public ExcelHelper(File file, String[] title){
        this.title = title;
        this.isWrite = 1;
        log.debug(isWrite);
        init(file,"test");
        //初始化工作簿标题栏
        writeRow(0,titleName);
    }

    /**
     * @description 默认的构造方法,只是为了继承使用的(ExcelStyleHelper这个类会继承)
     */
    public ExcelHelper(){}
    
    /* 公共的方法  */
    /** 
     * @description 初始化最大的行数和列数()
     * @author 陈吕奖 2018-08-01
     */ 
    public void initRowCol(){
        int temp = 0;
        cols = getRow(0).getLastCellNum();//下标从1开始
        rows = sheet.getLastRowNum();//下标从0开始
        //如果rows > 0,表示至少有两行,获取所有行中最大的列数
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
        //初始化excel对象(两种方式)
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
        //设置工作簿的名称
        if(sheetName != null) {
            wb.setSheetName(0, sheetName);
        }
        //将标题栏分割成两个(一个是标题,一个是字段)
        /*
         * 这样设计是为了获取数据中的数据,数据中返回的是一个Map
         * 我们需要通过字段名去获取数据
         */
        if(title != null) {
            //如果是创建表的话,标题就不会带:这个特殊符号去分割
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
     * @description 将List<Map> 转换成 List<String[]>
     * @author 陈吕奖 2018-07-24
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
     * @description 将wb输出到本地文件(注入样式)
     * 执行这个方法后会释放资源
     * @author 陈吕奖 2018-07-24
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
     * @description 关闭wb资源(读取玩文件后关闭资源)
     * 在pio中wb并没有关闭的方法
     * @author 陈吕奖 2018-08-01
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
     * @description 获取一个Cell,如果为空就创建一个
     * 提供x,y获取一个cell
     * @author 陈吕奖 2018-07-25
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
     * @description 获取一个Cell,如果为空就创建一个
     * 提供一个row来获取里面的row
     * @author 陈吕奖 2018-07-25
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
     * @description 获取一行,如果为空就创建一个
     * @author 陈吕奖 2018-07-26
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
     * @description 获取一行内的所有单元格
     * @author 陈吕奖 2018-08-01
     * @param row
     * @return
     */ 
    public HSSFCell[] getCellsByRow(HSSFRow row){
        //以最大的列数获取每一行数据
        HSSFCell[] cells = new HSSFCell[cols];
        for(int i = 0; i < cols; i++) {
            cells[i] = getCell(row, i);
        }
        return cells;
    }
    
    /** 
     * @description 获取一行内的所有单元格
     * @author 陈吕奖 2018-08-01
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
     * @description 获取一列内的所有单元格
     * @author 陈吕奖 2018-08-01
     * @param row
     * @return
     */ 
    public HSSFCell[] getCellsByCol(int colI){
        //获取所有的行
        HSSFCell[] cells = new HSSFCell[rows];
        for(int i = 0; i < rows; i++ ){
            HSSFRow row = sheet.getRow(i);
            //获取行中的所有列
            cells[i] = getCell(row, colI);
        }
        return cells;
    }
    
    /** 
     * @description 补齐(获取的一行数据个数应该和第一行相同,
     *              如果为空,将null变成""以便操作)
     * @author 陈吕奖 2018-06-27
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
     * @description 将一组单元格转换成为字符串数组
     * @author 陈吕奖 2018-06-27
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
     * @description (如果修改修改sheet样式,主动调用这个方法)
     * @author 陈吕奖 2018-07-26
     * @param style
     */ 
    public void setExcelStyleHelper(ExcelStyleHelper style) {
        this.style = style;
    }
    
    /*   excel读取的方法   */
    /** 
     * @description 获取指定一行的数据
     * @author 陈吕奖 2018-06-27
     * @param index
     * @return
     */ 
    public String[] getRowContextByIndex(int index){
        HSSFCell[] cells = getCellsByRow(index);
        return getCellsContext(cells);
    }
    
    /** 
     * @description 获取工作簿里面连续范围内的数据获取
     * @author 陈吕奖 2018-06-06
     * @param currentheet
     * @param range  length为0,表示获取全部的数据 
     *               length为1,表示从指定位置开始获取全部的数据
     *               length为2,表示一个范围,包含头包含尾
     * @return
     */
    public List<String[]> getRowByRange(int ... range){
        List<String[]> list = new ArrayList<String[]>();
        if(range.length == 0){
            //获取整张表的数据
            for(int i = 0; i < rows; i++){
                list.add(getRowContextByIndex(i));
            }
        }else if(range.length == 1){
            for(int i = range[0]; i < rows; i++){
                list.add(getRowContextByIndex(i));
            }
        }else{
            /* 如果开始和结束相同,表示只录入一条记录 */
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
     * @description 获取指定一列的数据
     * @author 陈吕奖 2018-06-27
     * @param index
     * @return
     */ 
    public String[] getColContextByIndex(int index){
        HSSFCell[] cells = getCellsByCol(index);
        return getCellsContext(cells);
    }
    
    /*   修改excel方法   */
    /** 
     * @description 修改单个单元格内容
     * @author 陈吕奖 2018-07-24
     * @param row
     * @param col
     * @param count
     */ 
    public void modifyCell(int row, int col, String context){
        HSSFCell cell = getCell(row,col);
        cell.setCellValue(context);
    }
    
    /** 
     * @description 修改一行的数据
     * @author 陈吕奖 2018-07-24
     * @param row
     * @param context
     */ 
    public void modifyRow(int row, String[] context){
        //获取有效的单元格个数
        short cols = (short)context.length;
        for(int i = 0; i < cols; i++) {
            HSSFCell cell = getCell(row,i);
            cell.setCellValue(context[i]);
        }
    }

    /** 
     * @description 修改一行的数据(从指定的列开始)
     * @author 陈吕奖 2018-07-24
     * @param row
     * @param col
     * @param context
     */ 
    public void modifyRow(int row, int col, String[] context){
        //获取有效的单元格个数
        short cols = (short)context.length;
        for(int i = 0; i < cols; i++) {
            HSSFCell cell = getCell(row,col++);
            cell.setCellValue(context[i]);
        }
    }

    /*   创建excel方法    */
    /** 
     * @description 创建excel文件()
     * @author 陈吕奖 2018-07-24
     * @param list
     * @throws IOException
     */ 
    public void createExcel(List<String[]> list) throws IOException{
        // i:为第几行  j:为第几列
        for(int i = 0; i < list.size(); i++) {
            String[] strings = list.get(i);
            //行数加1,标题行占一行
            writeRow(i+1, strings);
        }
        write();
    }

    /**
     * @description 写一行数据
     * @author 陈吕奖 2018-07-24
     * @param rowNum
     * @param data
     */
    public void writeRow(int rowNum, String[] data){
        HSSFRow row=null;
        HSSFCell cell=null;
        row=sheet.createRow(rowNum);//新增一行
        for(int j = 0; j < data.length; j++) {
            cell=row.createCell((short)j);//新增一列  0
            cell.setCellValue(data[j]);//向单元格中写入数据
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
/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.helper.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.helper.BaseHelper;
import main.old.helper.excel.exception.ExcelException;
import main.utils.common.ArrayUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @description 操作Excel基类
 * @author 陈吕奖
 * @date 2019-01-30
 */  
public abstract class ExcelBaseHelper extends BaseHelper{
    
    public File excelFile;
    public Workbook wb;
    public List<Sheet> sheets = new ArrayList<Sheet>();
    public Sheet currentheet;
    protected int maxRow;
    private ExcelStyle excelStyle;
    
    public void init(){
        excelStyle = new ExcelStyle(this);
    }
    
    /** 
     * @description 获取矩形块内容
     * @author 陈吕奖 2019-01-31
     * @param range
     * @return
     */ 
    public List<String[]> getContextByRectangleRange(RectangleRange range){
        List<Cell[]> cells = getCellByRectangleRange(range);
        List<String[]> contextByRange = parseString(cells);
        return contextByRange;
    }
    
    /** 
     * @description 获取矩形范围内的单元格对象
     * @author 陈吕奖 2019-02-01
     * @param range
     * @return
     */ 
    public List<Cell[]> getCellByRectangleRange(RectangleRange range){
        List<Cell[]> cells = new ArrayList<Cell[]>();
        List<Cell[]> _cells = getMultiRowCellByRange(range.rowStart, range.rowEnd);
        for(Cell[] cell : _cells) {
            cells.add(ArrayUtils.subArray(cell, range.colStart, range.colEnd));
        }
        return cells;
    }
    
    /**
     * @description 获取多行的内容
     * @author 陈吕奖 2019-01-31
     * @param rowStart
     * @param rowEnd
     * @return
     */ 
    public List<String[]> getMultiRowContextByRange(int rowStart, int rowEnd){
        List<Cell[]> cells = getMultiRowCellByRange(rowStart, rowEnd);
        List<String[]> contexts = parseString(cells);
        return contexts;
    }
    
    /** 
     * @description 获取多行的范围的单元格对象
     * @author 陈吕奖 2019-02-01
     * @param rowStart
     * @param rowEnd
     * @return
     */ 
    public List<Cell[]> getMultiRowCellByRange(int rowStart, int rowEnd){
        List<Cell[]> cells = new ArrayList<Cell[]>();
        for(int i = rowStart; i <= rowEnd; i++){
            cells.add(getRowCellByIndex(i));
        }
        return cells;
    }
    
    /** 
     * @description 获取多列内容
     * @author 陈吕奖 2019-01-31
     * @param colStart
     * @param colEnd
     * @return
     */ 
    public List<String[]> getMultiColContextByRange(int colStart, int colEnd){
        List<Cell[]> cells = getMultiColCellByRange(colStart, colEnd);
        List<String[]> contextList = parseString(cells);
        return contextList;
    }
    
    /** 
     * @description 获取多列的单元对象
     * @author 陈吕奖 2019-02-01
     * @param colStart
     * @param colEnd
     * @return
     */ 
    public List<Cell[]> getMultiColCellByRange(int colStart, int colEnd){
        maxRow = currentheet.getLastRowNum();
        List<Cell[]> cells = new ArrayList<Cell[]>();
        List<Cell[]> _cells = getCellByRectangleRange(new RectangleRange(0, maxRow, colStart, colEnd));
        //初始化数组 显示列数据的数组
        for(int i = 0; i < _cells.get(0).length; i++){
            cells.add(new Cell[_cells.size()]);
        }
        for(int i = 0; i < _cells.size(); i++){
            for(int j = 0; j < _cells.get(i).length; j++){
                cells.get(j)[i] = _cells.get(i)[j];
            }
        }
        return cells;
    }

    /** 
     * @description 获取一行内容
     * @author 陈吕奖 2019-01-31
     * @param rowIndex
     * @return
     */ 
    public String[] getRowContextByIndex(int rowIndex){
        Cell[] cells = getRowCellByIndex(rowIndex);
        String[] contexts = parseString(cells);
        return contexts;
    }
    
    /** 
     * @description 获取单行的单元格对象
     * @author 陈吕奖 2019-02-01
     * @param rowIndex
     * @return
     */ 
    public Cell[] getRowCellByIndex(int rowIndex){
        Row row = getRow(rowIndex);
        int maxCols = row.getPhysicalNumberOfCells();
        log.debug(maxCols);
        Cell[] cells = new Cell[maxCols];
        for(int i = 0; i < maxCols; i++) {
            Cell cell = row.getCell(i);
            if(cell == null){
                cell = row.createCell(i);
            }
            cells[i] = cell;
        }
        return cells;
    }
    
    /**
     * @description 获取一列内容
     * @author 陈吕奖 2019-01-31
     * @param colindex
     * @return
     */
    public String[] getColContextByIndex(int colindex){
        Cell[] cells = getColCellByIndex(colindex);
        String[] contexts = parseString(cells);
        return contexts;
    }
    
    public Cell[] getColCellByIndex(int colindex){
        maxRow = currentheet.getLastRowNum();
        List<Cell[]> cellList = getCellByRectangleRange(new RectangleRange(0, maxRow, colindex, colindex));
        Cell[] cells = new Cell[cellList.size()];
        for(int i = 0; i < cells.length; i++){
            cells[i] = cellList.get(i)[0];
        }
        return cells;
    }
    
    /** 
     * @description 获取单元格内容
     * @author 陈吕奖 2019-01-31
     * @param rowIndex
     * @param colIndex
     * @return
     */ 
    public String getCellContext(int rowIndex, int colIndex){
        Cell cell = getCell(rowIndex, colIndex);
        String context = getCellContext(cell);
        return context;
    }

    public Cell getCell(int rowIndex, int colIndex){
        Row row = getRow(rowIndex);
        Cell cell = row.getCell(colIndex);
        if(cell == null){
            cell = row.createCell(colIndex);
        }
        return cell;
    }
    
    private String getCellContext(Cell cell)
    {
        return CellContextUtils.getCellContext(cell);
    }
   
    private Row getRow(int rowIndex){
        Row row = currentheet.getRow(rowIndex);
        if(row==null){
            row = currentheet.createRow(rowIndex);
        }
        return row;
    }
    
    private List<String[]> parseString(List<Cell[]> cells){
        List<String[]> contextByRange = new ArrayList<String[]>();
        for(Cell[] _cells : cells) {
            contextByRange.add(parseString(_cells));
        }
        return contextByRange;
    }
        
    private String[] parseString(Cell[] cells){
        String[] contexts = new String[cells.length];
        for(int i = 0; i < cells.length; i++) {
            contexts[i] = getCellContext(cells[i]);
        }
        return contexts;
    }
    
    public void modifyRow(int rowIndex, String[] context){
        modifyRow(rowIndex, 0, context);
    }
    
    public void modifyRow(int rowIndex, int startIndex, String[] context){
        //获取有效的单元格个数
        short cols = (short)context.length;
        for(short i = 0; i < cols; i++) {
            modifyCell(rowIndex, (short)(i + startIndex), context[i]);
        }
    }
    
    /** 
     * @description 修改一列内容
     * @author 陈吕奖 2019-01-31
     * @param colIndex
     * @param context
     */ 
    public void modifyCol(int colIndex, String[] context){
        modifyCol(colIndex, 0, context);
    }
    
    /** 
     * @description 修改一列内容(从指定列开始修改)
     * @author 陈吕奖 2019-01-31
     * @param colIndex
     * @param startIndex
     * @param context
     */ 
    public void modifyCol(int colIndex, int startIndex, String[] context){
        for(int rowIndex = 0; rowIndex < context.length; rowIndex++) {
            modifyCell(startIndex + rowIndex, colIndex, context[rowIndex]);
        }
    }
    
    /** 
     * @description 这里用一句话描述这个方法的作用
     * @author 陈吕奖 2019-01-31
     * @param rowIndex
     * @param colIndex
     * @param context
     */ 
    public void modifyCell(int rowIndex, int colIndex, String context){
        Cell cell = getCell(rowIndex,colIndex);
        cell.setCellValue(context);
    }
    
    /** 
     * @description 设置当前使用sheet
     * @author 陈吕奖 2019-01-30
     * @param index 下标从0开始
     */ 
    public void setCurrentSheet(int index){
        if(index >= sheets.size())
            throw new ExcelException("设置选项开出错");
        currentheet = sheets.get(index);
    }
    
    public void setStyle(Cell cell, int style){
        List<Cell> _cells = Arrays.asList(new Cell[]{cell});
        setCellStyle(_cells, style);
    }
    
    public void setStyle(List<Cell[]> cells, int style){
        for(Cell[] _cells : cells) {
            setStyle(_cells, style);
        }
    }

    /** 
     * @description 设置样式,这个工具类获取的Cell是这两种形式List<HSSFCell[]>  HSSFCell[]
     * @author 陈吕奖 2019-02-01
     * @param cells
     * @param style
     */    
    public void setStyle(Cell[] cells, int style){
        List<Cell> _cells = Arrays.asList(cells);
        setCellStyle(_cells, style);
    }
    
    private void setCellStyle(List<Cell> cells, int style){
        log.debug(excelStyle);
        excelStyle.setExcelStyle(cells, style);
    }
    
    public void setSheetStyle(int style){
        excelStyle.setSheetStyle(style);
    }
    
    /** 
     * @description 合并单元格
     * @author 陈吕奖 2019-02-02
     * @param range
     */ 
    public void mergeCells(RectangleRange range){
//        Region region = new Region(range.rowStart, (short)range.colStart, range.rowEnd, (short)range.colEnd);
//        log.debug(region);
//        log.debug(currentheet);
//        currentheet.addMergedRegion(region);
        currentheet.addMergedRegion(new CellRangeAddress(range.rowStart, range.rowEnd, range.colStart, range.colEnd));
    }
    
    //创建sheet并初始化sheet名字
    public void createSheet(String sheetName){
        sheets.add(wb.createSheet());
        wb.setSheetName(sheets.size() - 1, sheetName);
    }
    
    /**
     * 
     * @description 保存修改内容
     * @author 陈吕奖 2019-02-18
     */
    public void write(){
        try {
            writeFile();
        }catch (Exception e) {
            new ExcelException("写入excel文件出错");
        }
    }
    
    private void writeFile() throws Exception{
        FileOutputStream fos = new FileOutputStream(excelFile);
        wb.write(fos);
        fos.close();
    }
}
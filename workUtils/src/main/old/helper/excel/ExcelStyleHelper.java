package main.old.helper.excel;

import main.old.helper.excel.filter.CellFilterI;
import main.utils.common.StringUtils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;


/**
 * @description excel样式的帮助类(1.通过筛选列 2.比较单元格内容)(比较单元格内容的比较级比较高)
 *              1.还可以根据内容进行样式的添加(未完成) ????
 * @author 陈吕奖
 * @date 2018-07-25
 */
public class ExcelStyleHelper extends ExcelHelper {

    /**
     * @fields type 表达式的类型
     */ 
    public int type;
    
    /**
     * @fields styleArray 选择的的数组 {"行号:列号:样式编号"} {"3-5:1-4:1"} {"1-a:1-4:1"}
     *         选择要改变的样式的cell
     */
    public String[] selectArray;

    /**
     * @fields cellArray 单元格数组(根据单元格判断是否要改变样式)
     */
    public String[] cellArray;

    /**
     * @fields filter 单元格筛选器(目前只支持单个筛选器)
     */
    public CellFilterI filter;

    /**
     * @description 设置样式组(根据选择表达式设置样式)
     * @param styleArray
     */
    public ExcelStyleHelper(String[] selectArray) {
        this.selectArray = selectArray;
    }

    /**
     * @description 根据选择表达式和内容表达式设置样式
     * @param selectArray
     * @param cellArray
     * @param filter
     */
    public ExcelStyleHelper(String[] selectArray, String[] cellArray, CellFilterI filter) {
        this.selectArray = selectArray;
        this.cellArray = cellArray;
        this.filter = filter;
    }

    /**
     * @description 根据内容表达式设置样式
     * @param cellArray
     * @param filter
     */
    public ExcelStyleHelper(String[] cellArray, CellFilterI filter) {
        this.cellArray = cellArray;
        this.filter = filter;
    }
    
    /**
     * @description 设置样式,根据指定的样式id来设置样式
     * @author 陈吕奖 2018-07-26
     * @return
     */
    public void setExcelStyle(HSSFCell cell, int style) {
        //根据不同的style,为cell设置不同的样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        //如果使用cell.getCellStyle,那么修改的是所有的cell的样式
        switch (style) {
            case 1://可以使用一个对象进行封装
                //居中
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                //右边框
                cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                //设置背景色(设置背景颜色需要设置两个值)
                cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
                cellStyle.setFillForegroundColor(HSSFColor.RED.index);//前景填充色
                break;
            case 100:
                //设置宽高
                break;
            default:
                break;
        }
        //设置样式
        cell.setCellStyle(cellStyle);
        //设置单元格宽高
        setSheetStyle(1);
    }

    /**
     * @description 
     * @author 陈吕奖 2018-07-27
     * @param type 类型
     */
    public void setExcelStyleByArray() {
        String[] array;
        //切换不同的表达式
        if(type == 1){
            array = selectArray;
        }else{
            array = cellArray;
        }
        //在这里设置样式
        //解析styleArray
        for(String string : array) {
            expressionAnalytic(string);
        }
    }

    /** 
     * @description 解析表达式
     * @author 陈吕奖 2018-07-30
     * @param string
     */
    public void expressionAnalytic(String string) {
        String[] split = string.split(":");
        int[] row = parseIntArray(split[0]);
        int[] col = parseIntArray(split[1]);
        int style = StringUtils.parseInt(split[2]);//这个肯定是一个
        if(row.length == 2) {
            //设置多行
            if(row[1] == -2) {
                //设置到底的多行
                for(int i = row[0]; i <= sheet.getLastRowNum(); i++) {
                    setRowStyle(i, style);
                }
            } else {
                //设置两个数值之间的行数,包含头包含尾
                for(int i = row[0]; i <= row[1]; i++) {
                    setRowStyle(i, style);
                }
            }
        } else if(col.length == 2) {
            //设置多列
            if(col[1] == -2) {
                //设置到底的多列 ?? sheet.getRow(0).getLastCellNum()这里获取的列数,可能会比实际值小(应获取所有行的列数取最大值)
                for(int i = col[0]; i <= sheet.getRow(0).getLastCellNum(); i++) {
                    setColStyle(i, style);
                }
            } else {
                //设置两个数值之间的行数,包含头包含尾
                for(int i = col[0]; i <= col[1]; i++) {
                    setColStyle(i, style);
                }
            }
        } else if(row.length == 1 && col.length == 1 && row[0] != -1 && col[0] != -1) {
            //设置一个单元格
            setCellStyle(row[0], col[0], style);
        } else if(row.length == 1 && col.length == 1 && row[0] == -1 && col[0] != -1) {
            //设置一列
            setColStyle(col[0], style);
        } else if(row.length == 1 && col.length == 1 && row[0] != -1 && col[0] == -1) {
            //设置一行
            setRowStyle(row[0], style);
        }
    }

    /**
     * @description 修改一个sheet的样式(添加样式)(只能修改固定位置的样式) 只能设置一个单元格,一列,多列,一行,多行
     * @author 陈吕奖 2018-07-26
     * @param currentheet
     */
    public void setStyle(HSSFWorkbook wb, HSSFSheet sheetChanege) {
        this.wb = wb;
        this.sheet = sheetChanege;
        //根据选择数组进行样式的修改
        if(selectArray != null) {
            type = 1;
            setExcelStyleByArray();
        }
        if(cellArray != null && filter != null) {
            type = 2;
            setExcelStyleByArray();
        }
    }

    /** 
     * @description 设置一个单元格的样式(所有的设置样式的方法,都是调用这个)
     * @author 陈吕奖 2018-07-27
     * @param row
     * @param col
     * @param type
     * @param styleNum
     */
    public void setCellStyle(int row, int col, int styleNum) {
        HSSFRow row2 = getRow(row);
        HSSFCell cell = getCell(row, col);
        if(type == 1){
            //选中之后就设置样式
            setExcelStyle(cell, styleNum);
        }else if(type == 2){
            //满足要求才设置样式
            if(filter.accept(row2, cell)) {
                setExcelStyle(cell, styleNum);
            }
        }
    }

    /**
     * @description 设置一行的样式(设置有效的最大列数)
     * @author 陈吕奖 2018-07-26
     * @param row
     * @param styleNum
     */
    public void setRowStyle(int row, int styleNum) {
        HSSFRow row2 = getRow(row);
        //获取最大的有效列数
        short cols = row2.getLastCellNum();
        for(int i = 0; i <= cols; i++) {
            setCellStyle(row, i, 1);
        }
    }

    /**
     * @description 设置一列的样式
     * @author 陈吕奖 2018-07-26
     * @param row
     * @param styleNum
     */
    public void setColStyle(int col, int styleNum) {
        //获取工作簿最大的有效行数
        int rows = sheet.getLastRowNum();
        for(int i = 0; i <= rows; i++) {
            setCellStyle(i, col, 1);
        }
    }

    /**
     * @description 设置一整个sheet的样式(例如设置宽高)
     * 设置最基础的样式
     * @author 陈吕奖 2018-07-26
     * @param row
     * @param styleNum
     */
    public void setSheetStyle(int styleNum) {
        //sheet.setDefaultColumnWidth((short)20);
        //设置单行的宽度
        sheet.setColumnWidth((short) 0, (short) (40 * 256));
        //设置一行的高度有效(一行一行设置)
        //sheet.getRow(0).setHeight((short)(20*200));
    }

    /**
     * @description 将字符串转换成int[](对象特定的方法,放到特定的对象中) 在str字符串中可能包含 -(用于分割)
     *              a(all从某列或某行开始,包含全部)
     * @author 陈吕奖 2018-07-26
     * @param str
     * @return {-1}表示没有列或行信息 -2表示取下面的全部内容
     */
    public int[] parseIntArray(String str) {
        int[] table;//表示行和列的信息
        if(str.contains("-")) {
            //如果有-表示取一个范围
            table = new int[2];
            if(str.contains("a")) {
                //如果有a表示获取下面的全部内容,a只可能出现在后方,不可能在前面
                table[0] = StringUtils.parseInt(str.split("-")[0]);
                table[1] = -2;
            } else {
                //获取一段范围
                table[0] = StringUtils.parseInt(str.split("-")[0]);
                table[1] = StringUtils.parseInt(str.split("-")[1]);
            }
        } else {
            //获取某一行或某一列数据
            table = new int[1];
            table[0] = StringUtils.parseInt(str);
        }
        return table;
    }
}
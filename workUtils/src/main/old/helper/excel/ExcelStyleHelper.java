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
 * @description excel��ʽ�İ�����(1.ͨ��ɸѡ�� 2.�Ƚϵ�Ԫ������)(�Ƚϵ�Ԫ�����ݵıȽϼ��Ƚϸ�)
 *              1.�����Ը������ݽ�����ʽ�����(δ���) ????
 * @author ������
 * @date 2018-07-25
 */
public class ExcelStyleHelper extends ExcelHelper {

    /**
     * @fields type ���ʽ������
     */ 
    public int type;
    
    /**
     * @fields styleArray ѡ��ĵ����� {"�к�:�к�:��ʽ���"} {"3-5:1-4:1"} {"1-a:1-4:1"}
     *         ѡ��Ҫ�ı����ʽ��cell
     */
    public String[] selectArray;

    /**
     * @fields cellArray ��Ԫ������(���ݵ�Ԫ���ж��Ƿ�Ҫ�ı���ʽ)
     */
    public String[] cellArray;

    /**
     * @fields filter ��Ԫ��ɸѡ��(Ŀǰֻ֧�ֵ���ɸѡ��)
     */
    public CellFilterI filter;

    /**
     * @description ������ʽ��(����ѡ����ʽ������ʽ)
     * @param styleArray
     */
    public ExcelStyleHelper(String[] selectArray) {
        this.selectArray = selectArray;
    }

    /**
     * @description ����ѡ����ʽ�����ݱ��ʽ������ʽ
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
     * @description �������ݱ��ʽ������ʽ
     * @param cellArray
     * @param filter
     */
    public ExcelStyleHelper(String[] cellArray, CellFilterI filter) {
        this.cellArray = cellArray;
        this.filter = filter;
    }
    
    /**
     * @description ������ʽ,����ָ������ʽid��������ʽ
     * @author ������ 2018-07-26
     * @return
     */
    public void setExcelStyle(HSSFCell cell, int style) {
        //���ݲ�ͬ��style,Ϊcell���ò�ͬ����ʽ
        HSSFCellStyle cellStyle = wb.createCellStyle();
        //���ʹ��cell.getCellStyle,��ô�޸ĵ������е�cell����ʽ
        switch (style) {
            case 1://����ʹ��һ��������з�װ
                //����
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                //�ұ߿�
                cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                //���ñ���ɫ(���ñ�����ɫ��Ҫ��������ֵ)
                cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//����ǰ�������ʽ
                cellStyle.setFillForegroundColor(HSSFColor.RED.index);//ǰ�����ɫ
                break;
            case 100:
                //���ÿ��
                break;
            default:
                break;
        }
        //������ʽ
        cell.setCellStyle(cellStyle);
        //���õ�Ԫ����
        setSheetStyle(1);
    }

    /**
     * @description 
     * @author ������ 2018-07-27
     * @param type ����
     */
    public void setExcelStyleByArray() {
        String[] array;
        //�л���ͬ�ı��ʽ
        if(type == 1){
            array = selectArray;
        }else{
            array = cellArray;
        }
        //������������ʽ
        //����styleArray
        for(String string : array) {
            expressionAnalytic(string);
        }
    }

    /** 
     * @description �������ʽ
     * @author ������ 2018-07-30
     * @param string
     */
    public void expressionAnalytic(String string) {
        String[] split = string.split(":");
        int[] row = parseIntArray(split[0]);
        int[] col = parseIntArray(split[1]);
        int style = StringUtils.parseInt(split[2]);//����϶���һ��
        if(row.length == 2) {
            //���ö���
            if(row[1] == -2) {
                //���õ��׵Ķ���
                for(int i = row[0]; i <= sheet.getLastRowNum(); i++) {
                    setRowStyle(i, style);
                }
            } else {
                //����������ֵ֮�������,����ͷ����β
                for(int i = row[0]; i <= row[1]; i++) {
                    setRowStyle(i, style);
                }
            }
        } else if(col.length == 2) {
            //���ö���
            if(col[1] == -2) {
                //���õ��׵Ķ��� ?? sheet.getRow(0).getLastCellNum()�����ȡ������,���ܻ��ʵ��ֵС(Ӧ��ȡ�����е�����ȡ���ֵ)
                for(int i = col[0]; i <= sheet.getRow(0).getLastCellNum(); i++) {
                    setColStyle(i, style);
                }
            } else {
                //����������ֵ֮�������,����ͷ����β
                for(int i = col[0]; i <= col[1]; i++) {
                    setColStyle(i, style);
                }
            }
        } else if(row.length == 1 && col.length == 1 && row[0] != -1 && col[0] != -1) {
            //����һ����Ԫ��
            setCellStyle(row[0], col[0], style);
        } else if(row.length == 1 && col.length == 1 && row[0] == -1 && col[0] != -1) {
            //����һ��
            setColStyle(col[0], style);
        } else if(row.length == 1 && col.length == 1 && row[0] != -1 && col[0] == -1) {
            //����һ��
            setRowStyle(row[0], style);
        }
    }

    /**
     * @description �޸�һ��sheet����ʽ(�����ʽ)(ֻ���޸Ĺ̶�λ�õ���ʽ) ֻ������һ����Ԫ��,һ��,����,һ��,����
     * @author ������ 2018-07-26
     * @param currentheet
     */
    public void setStyle(HSSFWorkbook wb, HSSFSheet sheetChanege) {
        this.wb = wb;
        this.sheet = sheetChanege;
        //����ѡ�����������ʽ���޸�
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
     * @description ����һ����Ԫ�����ʽ(���е�������ʽ�ķ���,���ǵ������)
     * @author ������ 2018-07-27
     * @param row
     * @param col
     * @param type
     * @param styleNum
     */
    public void setCellStyle(int row, int col, int styleNum) {
        HSSFRow row2 = getRow(row);
        HSSFCell cell = getCell(row, col);
        if(type == 1){
            //ѡ��֮���������ʽ
            setExcelStyle(cell, styleNum);
        }else if(type == 2){
            //����Ҫ���������ʽ
            if(filter.accept(row2, cell)) {
                setExcelStyle(cell, styleNum);
            }
        }
    }

    /**
     * @description ����һ�е���ʽ(������Ч���������)
     * @author ������ 2018-07-26
     * @param row
     * @param styleNum
     */
    public void setRowStyle(int row, int styleNum) {
        HSSFRow row2 = getRow(row);
        //��ȡ������Ч����
        short cols = row2.getLastCellNum();
        for(int i = 0; i <= cols; i++) {
            setCellStyle(row, i, 1);
        }
    }

    /**
     * @description ����һ�е���ʽ
     * @author ������ 2018-07-26
     * @param row
     * @param styleNum
     */
    public void setColStyle(int col, int styleNum) {
        //��ȡ������������Ч����
        int rows = sheet.getLastRowNum();
        for(int i = 0; i <= rows; i++) {
            setCellStyle(i, col, 1);
        }
    }

    /**
     * @description ����һ����sheet����ʽ(�������ÿ��)
     * �������������ʽ
     * @author ������ 2018-07-26
     * @param row
     * @param styleNum
     */
    public void setSheetStyle(int styleNum) {
        //sheet.setDefaultColumnWidth((short)20);
        //���õ��еĿ��
        sheet.setColumnWidth((short) 0, (short) (40 * 256));
        //����һ�еĸ߶���Ч(һ��һ������)
        //sheet.getRow(0).setHeight((short)(20*200));
    }

    /**
     * @description ���ַ���ת����int[](�����ض��ķ���,�ŵ��ض��Ķ�����) ��str�ַ����п��ܰ��� -(���ڷָ�)
     *              a(all��ĳ�л�ĳ�п�ʼ,����ȫ��)
     * @author ������ 2018-07-26
     * @param str
     * @return {-1}��ʾû���л�����Ϣ -2��ʾȡ�����ȫ������
     */
    public int[] parseIntArray(String str) {
        int[] table;//��ʾ�к��е���Ϣ
        if(str.contains("-")) {
            //�����-��ʾȡһ����Χ
            table = new int[2];
            if(str.contains("a")) {
                //�����a��ʾ��ȡ�����ȫ������,aֻ���ܳ����ں�,��������ǰ��
                table[0] = StringUtils.parseInt(str.split("-")[0]);
                table[1] = -2;
            } else {
                //��ȡһ�η�Χ
                table[0] = StringUtils.parseInt(str.split("-")[0]);
                table[1] = StringUtils.parseInt(str.split("-")[1]);
            }
        } else {
            //��ȡĳһ�л�ĳһ������
            table = new int[1];
            table[0] = StringUtils.parseInt(str);
        }
        return table;
    }
}
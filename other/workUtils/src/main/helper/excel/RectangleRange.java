/*
 * Copyright (c) 2019  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.helper.excel;

/** 
 * @description ��ʾexcel��һ�����η�Χ
 * @author ������
 * @date 2019-01-31 
 */
public class RectangleRange {

    public int rowStart = 0;
    public int rowEnd = 0;
    public int colStart = 0;
    public int colEnd = 0;
    /**
     * @description ������һ�仰����������캯��������
     * @param rowStart
     * @param rowEnd
     * @param colStart
     * @param colEnd
     */
    public RectangleRange(int rowStart, int rowEnd, int colStart, int colEnd) {
        super();
        this.rowStart = rowStart;
        this.rowEnd = rowEnd;
        this.colStart = colStart;
        this.colEnd = colEnd;
        adjust();
    }
    
    //adjust ����
    private void adjust(){
        if(rowStart > rowEnd){
            rowStart = rowStart ^ rowEnd;//�µ�rowStart^rowStart���rowEnd,^endStart���rowStart
            rowEnd = rowStart ^ rowEnd;
            rowStart = rowStart ^ rowEnd;
        }
        if(colStart > colEnd){
            colStart = colStart ^ colEnd;
            colEnd = colStart ^ colEnd;
            colStart = colStart ^ colEnd;
        }
    }
    
}

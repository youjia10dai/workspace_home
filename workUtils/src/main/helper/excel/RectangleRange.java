/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.helper.excel;

/** 
 * @description 表示excel中一个矩形范围
 * @author 陈吕奖
 * @date 2019-01-31 
 */
public class RectangleRange {

    public int rowStart = 0;
    public int rowEnd = 0;
    public int colStart = 0;
    public int colEnd = 0;
    /**
     * @description 这里用一句话描述这个构造函数的作用
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
    
    //adjust 调整
    private void adjust(){
        if(rowStart > rowEnd){
            rowStart = rowStart ^ rowEnd;//新的rowStart^rowStart变成rowEnd,^endStart变成rowStart
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

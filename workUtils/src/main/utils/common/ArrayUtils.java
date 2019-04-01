/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.utils.common;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

/** 
 * @description 实现数组的截取
 * @author 陈吕奖
 * @date 2019-01-31 
 */
public class ArrayUtils {

    public static String[] subArray(String[] old, int start, int end){
        end = end + 1;
        String[] fresh = new String[end - start];
        for(int i = 0; i < fresh.length; i++) {
            fresh[i] = old[i + start];
        }
        return fresh;
    }
    
    public static Cell[] subArray(Cell[] old, int start, int end){
        end = end + 1;
        Cell[] fresh = new Cell[end - start];
        for(int i = 0; i < fresh.length; i++) {
            fresh[i] = old[i + start];
        }
        return fresh;
    }
    
}

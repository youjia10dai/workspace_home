/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.procedure.source;

import java.util.List;

/** 
 * @description 回调函数(使用接口的方式来实现)
 * @author 陈吕奖
 * @date 2018-10-18 
 */
public interface ForEach <T>{
    
    /** 
     * @description 回调函数接口
     * @author 陈吕奖 2018-10-18
     * @param list 集合对象
     * @param index 小标
     * @param element 元素
     */
    public void forEach(List<T> list, int index, T element);
}
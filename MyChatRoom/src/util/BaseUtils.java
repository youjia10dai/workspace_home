/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package util;

import org.apache.log4j.Logger;

/** 
 * @description 基础类
 * 继承后可以使用log
 * @author 陈吕奖
 * @date 2018-12-07 
 */
public class BaseUtils {
    public final Logger log = Logger.getLogger(this.getClass());
    
    public void logError(Exception e){
        log.debug(e.getMessage());
    }
}
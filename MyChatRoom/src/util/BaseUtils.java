/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package util;

import org.apache.log4j.Logger;

/** 
 * @description ������
 * �̳к����ʹ��log
 * @author ������
 * @date 2018-12-07 
 */
public class BaseUtils {
    public final Logger log = Logger.getLogger(this.getClass());
    
    public void logError(Exception e){
        log.debug(e.getMessage());
    }
}
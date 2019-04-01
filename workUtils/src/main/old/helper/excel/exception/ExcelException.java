/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.old.helper.excel.exception;

/** 
 * @description excel操作的相关异常信息
 * @author 陈吕奖
 * @date 2019-01-30 
 */
public class ExcelException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(Throwable cause) {
        super(cause);
    }

}
/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.procedure.source.exception;

/** 
 * @description 无效的过程项
 * @author 陈吕奖
 * @date 2018-12-04 
 */
public class InvalidProcedure extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public InvalidProcedure(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidProcedure(String message) {
        super(message);
    }

    public InvalidProcedure(Throwable cause) {
        super(cause);
    }
}

/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */
package main.app.njry.procedure.detailtable.exception;

/**
 * @description 无效表类型异常
 * @author 陈吕奖
 * @date 2018-12-03
 */
public class InvalidTableType extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidTableType(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTableType(String message) {
        super(message);
    }

    public InvalidTableType(Throwable cause) {
        super(cause);
    }

}

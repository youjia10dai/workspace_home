/*
 * Copyright (c) 2019  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.email;

/** 
 * @description 邮件日报异常
 * @author 陈吕奖
 * @date 2019-01-28 
 */
public class EmailException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailException(String message) {
        super(message);
    }

    public EmailException(Throwable cause) {
        super(cause);
    }
}

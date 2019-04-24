/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.study.shiro;

/** 
 * @description Shiro安全控件的所有异常
 * @author 陈吕奖
 * @date 2018-12-29 
 */
public class ShiroException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ShiroException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShiroException(String message) {
        super(message);
    }

    public ShiroException(Throwable cause) {
        super(cause);
    }
    
}

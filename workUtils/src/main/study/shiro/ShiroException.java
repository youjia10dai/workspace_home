/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.study.shiro;

/** 
 * @description Shiro��ȫ�ؼ��������쳣
 * @author ������
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

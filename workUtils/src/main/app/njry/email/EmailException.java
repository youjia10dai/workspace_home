/*
 * Copyright (c) 2019  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.email;

/** 
 * @description �ʼ��ձ��쳣
 * @author ������
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

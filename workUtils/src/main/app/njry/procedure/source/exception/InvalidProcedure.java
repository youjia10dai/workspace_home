/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.procedure.source.exception;

/** 
 * @description ��Ч�Ĺ�����
 * @author ������
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

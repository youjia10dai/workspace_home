/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */
package main.app.njry.procedure.detailtable.exception;

/**
 * @description ��Ч�������쳣
 * @author ������
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

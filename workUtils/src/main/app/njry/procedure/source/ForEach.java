/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.procedure.source;

import java.util.List;

/** 
 * @description �ص�����(ʹ�ýӿڵķ�ʽ��ʵ��)
 * @author ������
 * @date 2018-10-18 
 */
public interface ForEach <T>{
    
    /** 
     * @description �ص������ӿ�
     * @author ������ 2018-10-18
     * @param list ���϶���
     * @param index С��
     * @param element Ԫ��
     */
    public void forEach(List<T> list, int index, T element);
}
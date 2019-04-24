/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.source.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/** 
 * @description ������һ�仰��������������
 * @author ������
 * @date 2018-08-13 
 */
public class ListUtils {

    /**
     * ��������������ָ��λ�õ�Ԫ��, ������Ԫ��λ���м仹������Ԫ�أ���Ҫʵ���м�Ԫ�ص�ǰ�ƻ���ƵĲ�����
     * @param list ����
     * @param oldPosition ��Ҫ������Ԫ��
     * @param newPosition ��������Ԫ��
     * @param <T>
     */
    public static <T> void swap1(List<T> list, int oldPosition, int newPosition){
        if(null == list){
            throw new IllegalStateException("The list can not be empty...");
        }
        T tempElement = list.get(oldPosition);

        // ��ǰ�ƶ���ǰ���Ԫ����Ҫ����ƶ�
        if(oldPosition < newPosition){
            for(int i = oldPosition; i < newPosition; i++){
                list.set(i, list.get(i + 1));
            }
            list.set(newPosition, tempElement);
        }
        // ����ƶ��������Ԫ����Ҫ��ǰ�ƶ�
        if(oldPosition > newPosition){
            for(int i = oldPosition; i > newPosition; i--){
                list.set(i, list.get(i - 1));
            }
            list.set(newPosition, tempElement);
        }
    }

    /**
     * ��������������ָ��λ�õ�Ԫ��, ������Ԫ��λ���м仹������Ԫ�أ���Ҫʵ���м�Ԫ�ص�ǰ�ƻ���ƵĲ�����
     * @param list ����
     * @param oldPosition ��Ҫ������Ԫ��
     * @param newPosition ��������Ԫ��
     * @param <T>
     */
    public static <T> void swap2(List<T> list, int oldPosition, int newPosition){
        if(null == list){
            throw new IllegalStateException("The list can not be empty...");
        }

        // ��ǰ�ƶ���ǰ���Ԫ����Ҫ����ƶ�
        if(oldPosition < newPosition){
            for(int i = oldPosition; i < newPosition; i++){
                Collections.swap(list, i, i + 1);
            }
        }

        // ����ƶ��������Ԫ����Ҫ��ǰ�ƶ�
        if(oldPosition > newPosition){
            for(int i = oldPosition; i > newPosition; i--){
                Collections.swap(list, i, i - 1);
            }
        }
    }
    public  static <T> void main(String[] args){
        //ʹ�����еĶ���ʵ���������
        Deque<String> list1 = new LinkedList<String>();
        list1.add("a");
        list1.add("b");
        list1.add("c");
        list1.add("d");
        list1.add("e");
        list1.add("f");
        list1.add("g");
        String last = list1.pollLast();//������βԪ��
        list1.offerFirst(last);//����ͷ�����Ϣ
        for (String e : list1) {
            System.out.print(e + " ");
        }
        
        System.out.println("");
        //ʹ�ù�����
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        for (String e : list) {
            System.out.print(e + " ");
        }
        System.out.println("");
        ListUtils.swap2(list, 2, 0);
        for (String e : list) {
            System.out.print(e + " ");
        }
        System.out.println("");
    }
}

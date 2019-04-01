/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.source.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/** 
 * @description 这里用一句话描述这个类的作用
 * @author 陈吕奖
 * @date 2018-08-13 
 */
public class ListUtils {

    /**
     * 调换集合中两个指定位置的元素, 若两个元素位置中间还有其他元素，需要实现中间元素的前移或后移的操作。
     * @param list 集合
     * @param oldPosition 需要调换的元素
     * @param newPosition 被调换的元素
     * @param <T>
     */
    public static <T> void swap1(List<T> list, int oldPosition, int newPosition){
        if(null == list){
            throw new IllegalStateException("The list can not be empty...");
        }
        T tempElement = list.get(oldPosition);

        // 向前移动，前面的元素需要向后移动
        if(oldPosition < newPosition){
            for(int i = oldPosition; i < newPosition; i++){
                list.set(i, list.get(i + 1));
            }
            list.set(newPosition, tempElement);
        }
        // 向后移动，后面的元素需要向前移动
        if(oldPosition > newPosition){
            for(int i = oldPosition; i > newPosition; i--){
                list.set(i, list.get(i - 1));
            }
            list.set(newPosition, tempElement);
        }
    }

    /**
     * 调换集合中两个指定位置的元素, 若两个元素位置中间还有其他元素，需要实现中间元素的前移或后移的操作。
     * @param list 集合
     * @param oldPosition 需要调换的元素
     * @param newPosition 被调换的元素
     * @param <T>
     */
    public static <T> void swap2(List<T> list, int oldPosition, int newPosition){
        if(null == list){
            throw new IllegalStateException("The list can not be empty...");
        }

        // 向前移动，前面的元素需要向后移动
        if(oldPosition < newPosition){
            for(int i = oldPosition; i < newPosition; i++){
                Collections.swap(list, i, i + 1);
            }
        }

        // 向后移动，后面的元素需要向前移动
        if(oldPosition > newPosition){
            for(int i = oldPosition; i > newPosition; i--){
                Collections.swap(list, i, i - 1);
            }
        }
    }
    public  static <T> void main(String[] args){
        //使用现有的对象实现这个方法
        Deque<String> list1 = new LinkedList<String>();
        list1.add("a");
        list1.add("b");
        list1.add("c");
        list1.add("d");
        list1.add("e");
        list1.add("f");
        list1.add("g");
        String last = list1.pollLast();//弹出队尾元素
        list1.offerFirst(last);//队列头添加信息
        for (String e : list1) {
            System.out.print(e + " ");
        }
        
        System.out.println("");
        //使用工具类
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

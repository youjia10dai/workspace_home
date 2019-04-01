/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.helper.document;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/** 
 * @description 解析dom对象的帮助类
 * @author 陈吕奖
 * @date 2018-06-29 
 */
public class DomAnalysis {
    /**
     * 下载网页源码让后在爬取想要的信息
     * 
     * @throws Exception
     */
    public static void test() throws Exception {
        Document document = Jsoup.parse(new File("D:\\" + "新建文本文档 (3).txt"),
                "utf-8");
        Elements elements = document.getElementsByTag("img");
        for (Element element : elements) {
            System.out.println(element);
        }
    }
    public static void main(String[] args) throws Exception {
        test();
    }
    
}

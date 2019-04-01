/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.helper.document;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/** 
 * @description ����dom����İ�����
 * @author ������
 * @date 2018-06-29 
 */
public class DomAnalysis {
    /**
     * ������ҳԴ���ú�����ȡ��Ҫ����Ϣ
     * 
     * @throws Exception
     */
    public static void test() throws Exception {
        Document document = Jsoup.parse(new File("D:\\" + "�½��ı��ĵ� (3).txt"),
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

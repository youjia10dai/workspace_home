package main.helper.document;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

/**
 * @author ������
 * ����dom��Ӧ�İ�����
 */
@Component()
public class DomHelper {

	public final Logger log = Logger.getLogger(this.getClass());
	/**
	 * ��ȡһ��Dom����,Ĭ���ַ���utf-8
	 * @param file
	 * @return
	 */
	public Document getDom(File file)
	{
		Document document = null;
        try {
        	document = Jsoup.parse(file, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}
	
	/**
	 * ����ָ�����ַ���,��ȡһ��dom����
	 * @param file
	 * @param charaset
	 * @return
	 */
	public Document getDom(File file, String charaset)
	{
		Document document = null;
        try {
        	document = Jsoup.parse(file, charaset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}
	
	/**
	 * ���ݱ��ʽ��ȡElement����
	 * @param dom    dom����
	 * @param regex  ���ʽ
	 * @return
	 */
	public Elements getElements(Document dom, String regex)
	{
		Elements elements = dom.select(regex);
		return elements;
	}
	
	/**
	 * ��ȡElement����ָ�����Ե�ֵ
	 * @param elements
	 * @param attribute
	 * @return
	 */
	public List<String> getElementsContext(Elements elements)
	{
		List<String> values = new ArrayList<String>();
		Elements ele = new Elements();
		for(Element element : elements)
		{
			
			if("input".equals(element.nodeName()))
			{
				//��������ֱ������
				if("button".equals(element.attr("type")) || "submit".equals(element.attr("type"))|| "reset".equals(element.attr("type")))
				{
					log.debug(element.attr("type"));
					continue;
				}
			}
			//���������ӱ��,��ʹ��String[]�����յ�
			if("checkbox".equals(element.attr("type"))){
				values.add("," +element.attr("name"));
				continue;
			}
			values.add(element.attr("name"));
		}
		return values;
	}
	
}

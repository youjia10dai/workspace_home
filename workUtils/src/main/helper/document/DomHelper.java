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
 * @author 陈吕奖
 * 操作dom对应的帮助类
 */
@Component()
public class DomHelper {

	public final Logger log = Logger.getLogger(this.getClass());
	/**
	 * 获取一个Dom对象,默认字符是utf-8
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
	 * 根据指定的字符集,获取一个dom对象
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
	 * 根据表达式获取Element对象
	 * @param dom    dom对象
	 * @param regex  表达式
	 * @return
	 */
	public Elements getElements(Document dom, String regex)
	{
		Elements elements = dom.select(regex);
		return elements;
	}
	
	/**
	 * 获取Element对象指定属性的值
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
				//特殊类型直接跳过
				if("button".equals(element.attr("type")) || "submit".equals(element.attr("type"))|| "reset".equals(element.attr("type")))
				{
					log.debug(element.attr("type"));
					continue;
				}
			}
			//下面两个加标记,是使用String[]来接收的
			if("checkbox".equals(element.attr("type"))){
				values.add("," +element.attr("name"));
				continue;
			}
			values.add(element.attr("name"));
		}
		return values;
	}
	
}

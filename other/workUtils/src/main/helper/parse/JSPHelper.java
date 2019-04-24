package main.helper.parse;

import java.io.File;
import java.util.List;

import main.helper.BaseHelper;
import main.helper.document.DomHelper;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 没什么用
 * @author 陈吕奖
 * 读取jsp文件,解析生成 获取表单提交的内容 
 */
@Component("JSPUtils")
public class JSPHelper extends BaseHelper{

	public static File file1 = new File("D:\\pzjtUpdate.jsp");
	
	public static String[] config = new String[]{"input",
												 "textarea","select"};
	
	private DomHelper dom;

	/**
	 * 获取Jsp页面的请求传递的参数
	 * @return
	 */
	public void getRequestParam(File file)
	{
		Document document = dom.getDom(file,"GBK");
		Elements elements = new Elements();
		for(int i = 0,j = config.length; i < j; i++){
			elements.addAll(dom.getElements(document, "form "+ config[i]));
		}
		
		List<String> list = dom.getElementsContext(elements);
		for(String str : list)
		{
			//如果字符串中包含,表示使用String[]来接收参数
			//生成获取html页面传递的值
			//String qtjz = req.getValue(request, "qtjz");   模板
			if(str.contains(",")){
				str = str.substring(1, str.length());
				System.out.println("String[] " + str + " = req.getValue(request, \""+str + "\");");
			}else{
				System.out.println("String " + str + " = req.getValue(request, \""+str + "\");");
			}
		}
	}
	
	/*        get  set         */
	public DomHelper getDom() {
		return dom;
	}
	
	@Autowired
	public void setDom(DomHelper dom) {
		this.dom = dom;
	}
}

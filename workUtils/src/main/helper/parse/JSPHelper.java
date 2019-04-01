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
 * ûʲô��
 * @author ������
 * ��ȡjsp�ļ�,�������� ��ȡ���ύ������ 
 */
@Component("JSPUtils")
public class JSPHelper extends BaseHelper{

	public static File file1 = new File("D:\\pzjtUpdate.jsp");
	
	public static String[] config = new String[]{"input",
												 "textarea","select"};
	
	private DomHelper dom;

	/**
	 * ��ȡJspҳ������󴫵ݵĲ���
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
			//����ַ����а���,��ʾʹ��String[]�����ղ���
			//���ɻ�ȡhtmlҳ�洫�ݵ�ֵ
			//String qtjz = req.getValue(request, "qtjz");   ģ��
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

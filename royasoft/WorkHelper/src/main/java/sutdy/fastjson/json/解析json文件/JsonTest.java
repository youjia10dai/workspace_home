package sutdy.fastjson.json.����json�ļ�;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import sutdy.fastjson.json.����json�ļ�.model.President;
import sutdy.fastjson.json.����json�ļ�.model.President.Arrayy;
import sutdy.fastjson.json.����json�ļ�.model.President.Arrayy.Member;
import sutdy.fastjson.json.����json�ļ�.model.Query;



/**
 * fastJson������
 * 
 * @author dmego
 *
 */
public class JsonTest {
	
	public static final Logger logger = LoggerFactory.getLogger(JsonTest.class);
	
	/**
	 * ��ȡ��·���µ������ļ� �����ɶ������鲢����
	 * 
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		// ��ȡ��·���µ�query.json�ļ�
		InputStream inputStream = this.getClass().getResourceAsStream("query.json");
		String jsontext = IOUtils.toString(inputStream, "utf8");
		logger.info(jsontext);
		// �Ƚ��ַ�jie��תΪList����
		List<Query> queryList = JSON.parseArray(jsontext, Query.class);
		System.out.println(queryList);
	}

	@Test
	public void test2() throws IOException {
		// ��ȡ��·���µ�query.json�ļ�
		InputStream inputStream = this.getClass().getResourceAsStream("president.json");
		String jsontext = IOUtils.toString(inputStream, "utf8");
		logger.info(jsontext);
		// �Ƚ��ַ�jie��תΪList����
		President jsonObject =  JSON.parseObject(jsontext, President.class);
		List<Arrayy> array = jsonObject.getArray();
		Arrayy arrayy = array.get(0);
		Integer[] cheakArr = arrayy.getCheakArr();
		System.out.println("111111  " + cheakArr[0]);
		Member member = arrayy.getMember();
		System.out.println(member.getName2());
		
	}
	
}

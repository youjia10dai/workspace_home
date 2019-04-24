package sutdy.fastjson.json.解析json文件;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import sutdy.fastjson.json.解析json文件.model.Query;



/**
 * fastJson测试类
 * 
 * @author dmego
 *
 */
public class JsonTest {
	
	public static final Logger logger = LoggerFactory.getLogger(JsonTest.class);
	
	/**
	 * 读取类路径下的配置文件 解析成对象数组并返回
	 * 
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		// 读取类路径下的query.json文件
		InputStream inputStream = this.getClass().getResourceAsStream("query.json");
		String jsontext = IOUtils.toString(inputStream, "utf8");
		logger.info(jsontext);
		// 先将字符jie串转为List数组
		List<Query> queryList = JSON.parseArray(jsontext, Query.class);
		System.out.println(queryList);
	}

}

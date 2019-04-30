package sutdy.httpclient;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSONObject;

public class Test {

	public static String postMethod(String url, JSONObject json) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			// 第一步：创建HttpClient对象
			httpClient = HttpClients.createDefault();

			// 第二步：创建httpPost对象
			HttpPost httpPost = new HttpPost(url);

			// 第三步：给httpPost设置JSON格式的参数
			StringEntity requestEntity = new StringEntity(json.toString(), "utf-8");
			requestEntity.setContentEncoding("UTF-8");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(requestEntity);

			// 第四步：发送HttpPost请求，获取返回值
			return httpClient.execute(httpPost, responseHandler);

		} catch (Exception e) {

		} finally {
			try {
				httpClient.close();
				// postMethod.releaseConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 第五步：处理返回值
		return "";
	}
	
	public static void main(String[] args) {
		Map<String, String> map = new HashedMap<String, String>();
        JSONObject requstParam = new JSONObject();
        map.put("servNumber", "14760544154");
        map.put("plantId", "SSHY");
        map.put("activityName", "111");
        requstParam.put("reqbody", JSONObject.toJSONString(map));
		
		String postMethod = postMethod("http://www.nx.10086.cn/ActPortal/nxmm/getToken", requstParam);
		System.out.println(postMethod);
	}
}

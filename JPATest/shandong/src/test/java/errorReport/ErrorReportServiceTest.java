package errorReport;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.royasoft.errorReport.api.interfaces.ErrorReportInterface;
import com.royasoft.errorReport.api.vo.ErrorInfoVo;
import com.royasoft.errorReport.impl.dao.ErrorReportDao;

@RunWith(SpringJUnit4ClassRunner.class)
//配置文件的位置
//若当前配置文件名=当前测试类名-context.xml 就可以在当前目录中查找@ContextConfiguration()
@ContextConfiguration("classpath*:spring_core.xml")
public class ErrorReportServiceTest {

	@Autowired
	private ErrorReportInterface interfaces;
	
	@Autowired
	private ErrorReportDao dao;
	
	@Test
	public void test(){
		System.out.println(interfaces);
	}
	
	@Test
	public void testAdd(){
		ErrorInfoVo vo = new ErrorInfoVo(new Date(), "clj218", "13851537761", "设备", "系统", "版本", "类型", "状态", "描述", "url地址");
		interfaces.add(vo);
	}
	
	@Test
	public void testUpdate(){
		ErrorInfoVo vo = new ErrorInfoVo(new Date(), "clj218", "13851537761", "设备", "系统", "版本", "类型", "状态2222", "描述1222", "url地址");
		vo.setId(3);
		interfaces.update(vo);
	}
	
	@Test
	public void testGetAllVersions(){
		String[] allVersions = interfaces.getAllVersions();
		for (String string : allVersions) {
			System.out.println(string);
		}
	}
	
	@Test
	public void testfindErrorInfoByPage(){
		// 条件查询
        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put("EQ_version", "版本1");
        conditions.put("EQ_type", "类型1");
        conditions.put("start_time_createdate", "2019-04-08 00:00:00");
        conditions.put("end_time_createdate", "2019-04-09 00:00:00");
//        Map<String, Boolean> sortMap = new HashMap<String, Boolean>();
		Map<String, Object> map = interfaces.findErrorInfoByPage(1, 10, conditions, null);
		System.out.println(JSON.toJSONString(map));
	}
}

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import helper.BaseHelper;
import helper.json.JsonHelper;


@RunWith(SpringJUnit4ClassRunner.class)
//�����ļ���λ��
//����ǰ�����ļ���=��ǰ��������-context.xml �Ϳ����ڵ�ǰĿ¼�в���@ContextConfiguration()
@ContextConfiguration("classpath:applicationContext.xml")
public class Main extends BaseHelper{

	
	
    @Autowired
    //�Զ�װ��
    private ApplicationContext cxf;
	
	/**
	 * ����SpringBean��ȡ
	 */
	@Test
	public void springBean(){
		JsonHelper word=(JsonHelper) cxf.getBean("jsonHelper");
		logger.debug("dddd,{},{}","fd","fd");
	}
	
	
	
}

package account;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.royasoft.account.api.interfaces.AccountInterface;
import com.royasoft.account.api.vo.AccountPageVo;
import com.royasoft.account.api.vo.AccountVo;

@RunWith(SpringJUnit4ClassRunner.class)
// 配置文件的位置
// 若当前配置文件名=当前测试类名-context.xml 就可以在当前目录中查找@ContextConfiguration()
@ContextConfiguration("classpath*:spring_core.xml")
public class AccountServiceTest {

    @Autowired
    private AccountInterface interfaces;


    /**
     * 新增
     */
    @Test
    public void test() {
        AccountVo accountVo = new AccountVo();
        try {
            accountVo.setUserId("11111");
            accountVo.setCreateTime("2019-04-21 12:12:12");// yyyy-MM-dd HH:mm:ss
            accountVo.setTelephone("dddddd");
            accountVo.setUserName("dddddd");
            accountVo.setDepartment("sssss");
            interfaces.add(accountVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分页获取
     */
    @Test
    public void test1() {
        JSONObject requestJson = new JSONObject();
        requestJson.put("telephone", "1");
        requestJson.put("userName", "1");
        try {
            Map<String, Object> condition = interfaces.builderCondition(requestJson);
            AccountPageVo findAccountByPage = interfaces.findAccountByPage(4, 2, condition);
            System.out.println(findAccountByPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

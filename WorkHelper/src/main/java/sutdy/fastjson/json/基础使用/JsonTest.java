package sutdy.fastjson.json.����ʹ��;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import com.alibaba.fastjson.JSON;
import sutdy.fastjson.json.����ʹ��.model.User;
import sutdy.fastjson.json.����ʹ��.model.UserGroup;



/**
 * fastJson������
 * @author dmego
 *
 */
public class JsonTest {
    /**
     * java����ת json�ַ��� 
     */
    @Test
    public void objectTOJson(){
        //��java��תjson�ַ���
        User user = new User("dmego", "123456");
        String UserJson = JSON.toJSONString(user);
        System.out.println("��java��תjson�ַ���:"+UserJson);
        
        //List<Object>תjson�ַ���
        User user1 = new User("zhangsan", "123123");
        User user2 = new User("lisi", "321321");
        List<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        String ListUserJson = JSON.toJSONString(users);
        System.out.println("List<Object>תjson�ַ���:"+ListUserJson);   
        
        //����java��תjson�ַ���
        UserGroup userGroup = new UserGroup("userGroup", users);
        String userGroupJson = JSON.toJSONString(userGroup);
        System.out.println("����java��תjson�ַ���:"+userGroupJson);       
        
    }
    
    /**
     * json�ַ���תjava����
     * ע���ַ�����ʹ��˫������Ҫת�� (" --> \"),����ʹ�õ��ǵ�����
     */
    @Test
    public void JsonTOObject(){
        /* json�ַ���ת��java����
         * �ַ�����{"password":"123456","username":"dmego"}*/
        
        String jsonStr1 = "{'password':'123456','username':'dmego'}";
        User user = JSON.parseObject(jsonStr1, User.class);
        System.out.println("json�ַ���ת��java����:"+user.toString());
        
        /*
         * json�ַ���תList<Object>����
         * �ַ�����[{"password":"123123","username":"zhangsan"},{"password":"321321","username":"lisi"}]
         */
        String jsonStr2 = "[{'password':'123123','username':'zhangsan'},{'password':'321321','username':'lisi'}]";
        List<User> users = JSON.parseArray(jsonStr2, User.class);
        System.out.println("json�ַ���תList<Object>����:"+users.toString());
            
        /*json�ַ���ת����java����
         * �ַ�����{"name":"userGroup","users":[{"password":"123123","username":"zhangsan"},{"password":"321321","username":"lisi"}]}
         * */
        String jsonStr3 = "{'name':'userGroup','users':[{'password':'123123','username':'zhangsan'},{'password':'321321','username':'lisi'}]}";
        UserGroup userGroup = JSON.parseObject(jsonStr3, UserGroup.class);
        System.out.println("json�ַ���ת����java����:"+userGroup);  
    }
}

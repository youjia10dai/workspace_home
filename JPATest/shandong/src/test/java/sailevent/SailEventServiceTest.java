package sailevent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.royasoft.sailevent.api.interfaces.SailEventInterface;
import com.royasoft.sailevent.impl.entity.SailClub;
import com.royasoft.sailevent.impl.entity.SailClubExtra;
import com.royasoft.sailevent.impl.entity.SailUser;

@RunWith(SpringJUnit4ClassRunner.class)
//配置文件的位置
//若当前配置文件名=当前测试类名-context.xml 就可以在当前目录中查找@ContextConfiguration()
@ContextConfiguration("classpath*:spring_core.xml")
public class SailEventServiceTest {

	@Autowired
	private SailEventInterface dao;
	
	@Test
	public void findByUserId(){
		SailUser user = dao.findSailUserById("2");
		if(user != null){
			System.out.println(user.getUserId());
			System.out.println(user.getAreaName());
			System.out.println(user.getCompanyName());
		}
	}
	
	@Test
	public void findSailClubByClubName() {
		SailClub club = dao.findSailClubByClubName("俱乐部1");
		if(club != null) {
			System.out.println(club.getClubId());
			System.out.println(club.getClubName());
		}
	}
	
	@Test
	public void saveExtraSailClub() {
		SailClubExtra club = new SailClubExtra();
		club.setUserId("1");
		club.setClubName("ddd");
		dao.saveExtraSailClub(club);
	}
	
	
	
}
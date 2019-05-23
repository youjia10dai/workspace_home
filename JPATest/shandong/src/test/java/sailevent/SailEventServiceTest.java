package sailevent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.royasoft.sailevent.api.interfaces.SailEventInterface;
import com.royasoft.sailevent.impl.entity.SailClub;
import com.royasoft.sailevent.impl.entity.SailClubExtra;
import com.royasoft.sailevent.impl.entity.SailPresident;
import com.royasoft.sailevent.impl.entity.SailUser;

@RunWith(SpringJUnit4ClassRunner.class)
// 配置文件的位置
// 若当前配置文件名=当前测试类名-context.xml 就可以在当前目录中查找@ContextConfiguration()
@ContextConfiguration("classpath*:spring_core.xml")
public class SailEventServiceTest {

	@Autowired
	private SailEventInterface dao;

	@Test
	public void findByUserId() throws Exception {
		SailUser user = dao.findSailUserById("2");
		if (user != null) {
			System.out.println(user.getUserId());
			System.out.println(user.getAreaName());
			System.out.println(user.getCompanyName());
			System.out.println(user.getSailClubs());
		}
	}

	@Test
	public void findSailClubByClubName() throws Exception {
		SailClub club = dao.findSailClubByClubName("俱乐部1");
		if (club != null) {
			System.out.println(club.getClubId());
			System.out.println(club.getClubName());
			Set<SailUser> sailUsers = club.getSailUsers();
			for (SailUser sailUser : sailUsers) {
				System.out.println(sailUser.getAreaName());
			}
		}
	}

	@Test
	public void saveExtraSailClub() throws Exception {
		SailClubExtra club = new SailClubExtra();
		club.setUserId("1");
		club.setClubName("ddd");
		dao.saveExtraSailClub(club);
	}

	@Test
	public void saveClubVote() throws Exception {
		SailUser user = dao.findSailUserById("2");
		SailUser user1 = dao.findSailUserById("3");
		Set<Integer> clubIds = new HashSet<Integer>();
		clubIds.add(1);
		clubIds.add(2);
		clubIds.add(33);
		Set<SailClub> set = new HashSet<SailClub>();
		set.addAll(dao.findByClubIds(clubIds));
		user.setSailClubs(set);
		user1.setSailClubs(set);
		List<SailUser> list = new ArrayList<SailUser>();
		list.add(user);
		list.add(user1);
		dao.saveClubVote(list);
	}

	@Test
	public void findAllClubs() throws Exception {
		List<SailClub> clubs = dao.findAllClubs();
		for (SailClub sailClub : clubs) {
			Set<SailUser> users = sailClub.getSailUsers();
			for (SailUser sailUser : users) {
				System.out.println(sailUser.getAreaName());
			}
			System.out.println("===================");
		}
	}
	
	@Test
	public void savePresident() throws Exception {
		SailPresident president = new SailPresident();

		List<SailPresident> presidents = new ArrayList<SailPresident>();
		presidents.add(president);
		dao.savePresident(presidents);
	}
	
	@Test
	public void getSelectResult() throws Exception {
		dao.getDefultClubs();
	}
	
}
package sutdy.spring.beanutils;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import sutdy.spring.beanutils.entity.SailClub;
import sutdy.spring.beanutils.entity.SailUser;
import sutdy.spring.beanutils.vo.SailClubVo;

public class BeanUtilsTest {

	public static void main(String[] args) throws Exception {
		SailClub sailClub = new SailClub();
		sailClub.setClubId(1);
		sailClub.setClubName("dddd");
		Set<SailUser> set = new HashSet<SailUser>();
		SailUser sailUser = new SailUser();
		sailUser.setUserId("1");
		sailUser.setCompanyName("dddd");
		sailUser.setAreaName("dddd");
		set.add(sailUser);
		sailClub.setSailUsers(set);
//		SailClubVo sailClubVo = new SailClubVo();
		SailClubVo sailClubVo = copyBean(sailClub, SailClubVo.class);
//		BeanUtils.copyProperties(sailClub, sailClubVo);
		System.out.println(sailClub);
		System.out.println(sailClubVo);
	}

	public void test1() {

	}

	public static <T> T copyBean(Object source, Class<T> target) throws Exception {
		T newBean = target.newInstance();
		BeanUtils.copyProperties(source, newBean);
		return newBean;
	}

	public static <T> List<T> copyBean(List<Object> sources, Class<T> target) throws Exception {
		List<T> newBeans = new ArrayList<T>();
		for (Object source : sources) {
			newBeans.add(copyBean(source, target));
		}
		return newBeans;
	}
	
	
	/*
	 * private SailClubExtra transVoToEntity(SailClubExtraVo vo) { SailClubExtra
	 * sailClubExtra = new SailClubExtra(); try { BeanUtils.copyProperties(vo,
	 * sailClubExtra); } catch (Exception e) { logger.error("VO转换实体对象异常", e); }
	 * return sailClubExtra; }
	 */
}

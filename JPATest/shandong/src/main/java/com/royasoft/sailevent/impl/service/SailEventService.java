package com.royasoft.sailevent.impl.service;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royasoft.sailevent.api.interfaces.SailEventInterface;
import com.royasoft.sailevent.impl.dao.SailClubDao;
import com.royasoft.sailevent.impl.dao.SailClubExtraDao;
import com.royasoft.sailevent.impl.dao.SailDefaultClubDao;
import com.royasoft.sailevent.impl.dao.SailPresidentDao;
import com.royasoft.sailevent.impl.dao.SailPresidentMemberDao;
import com.royasoft.sailevent.impl.dao.SailUserDao;
import com.royasoft.sailevent.impl.entity.SailClub;
import com.royasoft.sailevent.impl.entity.SailClubExtra;
import com.royasoft.sailevent.impl.entity.SailPresident;
import com.royasoft.sailevent.impl.entity.SailUser;

/**
 * @author clj
 * @date 2019-05-05
 * 启航行动的service
 */
@Transactional
@Service
public class SailEventService implements SailEventInterface {

	private final Logger logger = LoggerFactory.getLogger(SailEventService.class);
	
	@Autowired
	private SailUserDao userDao;
	
	@Autowired
	private SailClubDao clubDao;
	
	@Autowired
	private SailClubExtraDao clubExtraDao;
	
	@Autowired
	private SailPresidentDao presidentDao; 
	
	@Autowired
	private SailPresidentMemberDao memberDao;
	
	@Autowired
	private SailDefaultClubDao defaultClubDao;
	/**
	 * 根据ID查找
	 */
	@Override
	public SailUser findSailUserById(String user_id) {
		SailUser user = userDao.findOne(user_id);
		if(user != null) {
			return user;
		}
		return null;
	}

	/**
	 * 根据俱乐部名称,查找俱乐部
	 */
	@Override
	public SailClub findSailClubByClubName(String club_name) {
		SailClub club = clubDao.findByClubName(club_name);
		if(club != null) {
			return club;
		}
		return null;
	}

	/**
	 * 保存用户所动输入的俱乐部
	 */
	@Override
	public void saveExtraSailClub(SailClubExtra club) {
		clubExtraDao.save(club);
	}

	/**
	 * 保存用户俱乐部投票接口
	 */
	@Override
	public void saveClubVote(List<SailUser> users){
		userDao.save(users);
	}

	/**
	 * 根据俱乐部ID查找俱乐部
	 */
	@Override
	public List<SailClub> findByClubIds(Set<Integer> ids) throws Exception {
		List<SailClub> clubs = clubDao.findAll(ids);
		return clubs;
	}

	/**
	 * 获取所有的俱乐部
	 */
	@Override
	public List<SailClub> findAllClubs() throws Exception {
		List<SailClub> clubs = clubDao.findAll();
		return clubs;
	}

	/**
	 * 保存选举信息
	 */
	@Override
	public void savePresident(List<SailPresident> presidents) {
		presidentDao.save(presidents);
	}

	@Override
	public void getSelectResult() throws Exception {
		List<Object[]> findSelectResult = clubDao.findSelectResult();
		for (Object[] object : findSelectResult) {
			System.out.println(object[0]);
			System.out.println(object[1]);
		}
	}

	@Override
	public void getDefultClubs() throws Exception {
        int count = defaultClubDao.getClubIdByClubIdAndAreaName("合肥", 4);
        System.out.println(count);
        // List<Object> clubIdByAreaName = defaultClubDao.getClubIdByAreaName("黄山");

        // System.out.println(clubIdByAreaName);
	}

	
	
}
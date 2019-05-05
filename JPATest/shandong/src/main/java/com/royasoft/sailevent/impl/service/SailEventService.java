package com.royasoft.sailevent.impl.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.royasoft.sailevent.api.interfaces.SailEventInterface;
import com.royasoft.sailevent.impl.dao.SailClubDao;
import com.royasoft.sailevent.impl.dao.SailClubExtraDao;
import com.royasoft.sailevent.impl.dao.SailUserDao;
import com.royasoft.sailevent.impl.entity.SailClub;
import com.royasoft.sailevent.impl.entity.SailClubExtra;
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

}

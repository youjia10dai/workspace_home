package com.royasoft.sailevent.api.interfaces;

import com.royasoft.sailevent.impl.entity.SailClub;
import com.royasoft.sailevent.impl.entity.SailClubExtra;
import com.royasoft.sailevent.impl.entity.SailUser;

public interface SailEventInterface {

	/**
	 * 根据ID查找用户
	 * @param user_id
	 * @return
	 */
	SailUser findSailUserById(String user_id);
	
	/**
	 * 根据俱乐部名称查找俱乐部信息
	 * @param club_name
	 * @return
	 */
	SailClub findSailClubByClubName(String club_name);
	
	/**
	 * 保存用户手动输入的俱乐部
	 * @param club
	 * @return
	 */
	 void saveExtraSailClub(SailClubExtra club);
	
	 
	 
}
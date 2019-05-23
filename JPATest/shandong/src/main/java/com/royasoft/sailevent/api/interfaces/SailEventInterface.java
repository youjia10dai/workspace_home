package com.royasoft.sailevent.api.interfaces;

import java.util.List;
import java.util.Set;

import com.royasoft.sailevent.impl.entity.SailClub;
import com.royasoft.sailevent.impl.entity.SailClubExtra;
import com.royasoft.sailevent.impl.entity.SailPresident;
import com.royasoft.sailevent.impl.entity.SailUser;

public interface SailEventInterface {

	/**
	 * 根据ID查找用户
	 * @param user_id
	 * @return
	 */
	SailUser findSailUserById(String user_id) throws Exception;
	
	/**
	 * 根据俱乐部名称查找俱乐部信息
	 * @param club_name
	 * @return
	 */
	SailClub findSailClubByClubName(String club_name) throws Exception;
	
	/**
	 * 保存用户手动输入的俱乐部
	 * @param club
	 * @return
	 */
	 void saveExtraSailClub(SailClubExtra club) throws Exception;
	
	/**
	 * 保存用户俱乐部投票结果
     * @param user
  	 * @throws Exception
	 */
	 void saveClubVote(List<SailUser> users) throws Exception;
	 
	 /**
	  * 
	  * @param ids
	  * @return
	  * @throws Exception
	  */
	 List<SailClub> findByClubIds(Set<Integer> ids) throws Exception;
	 
	 /**
	  * 获取所有的俱乐部
	  * @return
	  * @throws Exception
	  */
 	 List<SailClub> findAllClubs() throws Exception;
	 
 	 /**
 	  * 保存选举信息
 	  * @param presidents
 	  * @throws Exception
 	  */
     void savePresident(List<SailPresident> presidents) throws Exception; 
     
     void getSelectResult() throws Exception;
     
     void getDefultClubs() throws Exception;
}
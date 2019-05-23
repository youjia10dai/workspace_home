package com.royasoft.sailevent.impl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.royasoft.sailevent.impl.entity.SailClub;

public interface SailClubDao extends JpaRepository<SailClub, Integer>{

	SailClub findByClubName(String club_name);
	
	@Query(value="select clubId, count(userId) from SailUserClub group by clubId")
	List<Object[]> findSelectResult();
	
}
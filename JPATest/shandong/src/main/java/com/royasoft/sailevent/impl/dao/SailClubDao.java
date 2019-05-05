package com.royasoft.sailevent.impl.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.royasoft.sailevent.impl.entity.SailClub;

public interface SailClubDao extends JpaRepository<SailClub, Integer>{

	SailClub findByClubName(String club_name);
	
}
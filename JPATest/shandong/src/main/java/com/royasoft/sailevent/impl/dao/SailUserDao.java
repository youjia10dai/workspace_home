package com.royasoft.sailevent.impl.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.royasoft.sailevent.impl.entity.SailUser;
import java.lang.Integer;

public interface SailUserDao extends JpaRepository<SailUser, String>{
	
}

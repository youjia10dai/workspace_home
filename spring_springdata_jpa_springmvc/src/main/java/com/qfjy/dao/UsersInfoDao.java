package com.qfjy.dao;

import org.springframework.data.repository.Repository;

import com.qfjy.bean.UsersInfo;

public interface UsersInfoDao extends Repository<UsersInfo, String>{

	public UsersInfo getById(String id);
}

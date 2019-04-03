package com.qfjy.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.qfjy.bean.Users;

public interface UsersPagingDao extends PagingAndSortingRepository<Users, Integer>{

}

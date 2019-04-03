package com.qfjy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qfjy.bean.Users;

public interface MyUsersDao extends JpaRepository<Users, Integer>, MyUsersRepository{
}

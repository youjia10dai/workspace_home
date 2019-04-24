package com.qfjy.dao.f自定义的Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qfjy.bean.Users;

public interface MyUsersDao extends JpaRepository<Users, Integer>, MyUsersRepository{
}

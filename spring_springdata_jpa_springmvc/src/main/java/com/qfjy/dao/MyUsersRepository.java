package com.qfjy.dao;

import com.qfjy.bean.Users;

/**
 *	自定以Repository接口
 *	继承这个接口,自己提供实现方法
 */
public interface MyUsersRepository {
	Users find(Integer id);
}

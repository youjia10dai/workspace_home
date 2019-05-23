package com.royasoft.account.impl.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.royasoft.account.impl.entity.Account;

public interface AccountDao extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

}
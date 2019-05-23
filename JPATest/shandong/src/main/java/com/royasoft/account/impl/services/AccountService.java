package com.royasoft.account.impl.services;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.royasoft.account.api.interfaces.AccountInterface;
import com.royasoft.account.api.vo.AccountPageVo;
import com.royasoft.account.api.vo.AccountVo;
import com.royasoft.account.impl.dao.AccountDao;
import com.royasoft.account.impl.entity.Account;
import com.royasoft.util.DynamicSpecifications;
import com.royasoft.util.SearchFilter;

/**
 * 用户注销服务
 * 
 * @author chenlj
 * @date 2019-05-16
 */
@Transactional
@Service
public class AccountService implements AccountInterface {

    private final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountDao accountDao;

    /**
     * 添加注销用户
     */
    @Override
    public AccountVo add(AccountVo accountVo) throws Exception {
        logger.debug("accountVo的userID为:{}", accountVo.getUserId());
        Account account = copyBean(accountVo, Account.class);
        account.setCreateTime(new Date());
        account = accountDao.save(account);
        accountVo = copyBean(account, AccountVo.class);
        return accountVo;
    }

    @Override
    public AccountPageVo findAccountByPage(int page, int rows, Map<String, Object> conditions) throws Exception {
        logger.debug("分页查询日志列表, page{}, rows{},conditions{}", page, rows, conditions);
        Map<String, Object> map = new HashMap<String, Object>();
        // 默认按时间降序
        PageRequest pageRequest = new PageRequest(page - 1, rows, new Sort(Direction.DESC, "createTime"));
        Map<String, SearchFilter> filters = SearchFilter.parse(conditions);
        Specification<Account> spec = DynamicSpecifications.bySearchFilter(filters.values(), Account.class);
        Page<Account> pages = accountDao.findAll(spec, pageRequest);
        AccountPageVo accountPageVo = new AccountPageVo();
        accountPageVo.setTotal(pages.getTotalPages());
        accountPageVo.setContent(transEntityToVo(pages.getContent()));
        return accountPageVo;
    }

    public List<AccountVo> transEntityToVo(List<Account> entitys) throws Exception {
        List<AccountVo> vos = new ArrayList<AccountVo>();
        for (Account entity : entitys) {
            AccountVo vo = copyBean(entity, AccountVo.class);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            vo.setCreateTime(sdf.format(entity.getCreateTime()));
            vos.add(vo);
        }
        return vos;
    }

    public static <T> T copyBean(Object source, Class<T> target) throws Exception {
        T newBean = target.newInstance();
        BeanUtils.copyProperties(source, newBean);
        return newBean;
    }

    @Override
    public Map<String, Object> builderCondition(JSONObject requestJson) throws Exception {
        //
        Map<String, Object> conditions = new HashMap<String, Object>();
        String telephone = requestJson.getString("telephone");
        if (telephone != null && !"".equals(telephone)) {
            conditions.put("LIKE_telephone", telephone);
        }
        String username = requestJson.getString("userName");
        if (username != null && !"".equals(username)) {
            conditions.put("LIKE_userName", username);
        }
        return conditions;
    }

}

package com.royasoft.account.api.interfaces;


import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.royasoft.account.api.vo.AccountPageVo;
import com.royasoft.account.api.vo.AccountVo;

/**
 * @author Clj 用户操作接口
 * @Date 2019-05-16
 */
public interface AccountInterface {


    /**
     * 注销用户
     * 
     * @param accountVo
     * @return
     */
    public AccountVo add(AccountVo accountVo) throws Exception;

    /**
     * 
     * @param page 页码
     * @param rows 一页的条数
     * @param conditions 查询条件
     * @param sortMap 排序方式
     * @return
     */
    public AccountPageVo findAccountByPage(int page, int rows, Map<String, Object> conditions) throws Exception;

    /**
     * 构建查询条件
     * 
     * @param requestJson @return @throws
     */
    public Map<String, Object> builderCondition(JSONObject requestJson) throws Exception;

}

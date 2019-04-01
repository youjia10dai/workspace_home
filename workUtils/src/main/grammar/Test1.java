/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.grammar;

import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

/** 
 * @description ������һ�仰��������������
 * @author ������
 * @date 2018-09-27 
 */

public class Test1 {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Order(1)
    public int queryForInt(String sql, Object... objects) {
        int exc = -1;
        try {
            exc = this.getJdbcTemplate().queryForObject(sql, objects, Integer.class);
            //            exc=this.getJdbcTemplate().queryForObject(sql, Integer.class);
        }
        catch (Exception e) {
            exc = -1;
            e.printStackTrace();
        }

        return exc;
    }
    
}

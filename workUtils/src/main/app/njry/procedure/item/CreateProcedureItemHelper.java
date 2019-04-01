/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.app.njry.procedure.item;

import main.helper.BaseHelper;
import main.spring.database.aspect.DataSource;
import main.spring.database.multDataSource.DBContextHolder;
import main.utils.common.DateUtils;
import main.utils.common.StringUtils;
import org.springframework.stereotype.Component;

/** 
 * @description 创建过程项的帮助类
 * 使用@tonjzw3的方式来
 * jtb.clj_20180831_tmp1@tonjzw3 暂时用这个表代替
 * 问题
 * 1.跨库可能存在权限的问题
 *   解决:配置两个数据库
 * @author 陈吕奖
 * @date 2018-07-17 
 */
@Component("createProItem")
public class CreateProcedureItemHelper extends BaseHelper{

    //获取最大的过程项,理论上可以使用
    public static String cntItemSql = "select max(cnt_item) from ngbossbk.t_proc_dict where (upper(cnt_user) like '%JTB%' or upper(cnt_user) like '%DEV%') and cnt_item not in (3001011,3000905)";
    
    //用于判断过程项是否存在的sql
    public static String cntItemExistsSql = "select count(1) from ngbossbk.t_proc_dict where cnt_item = ?";
    
    //3个问号     第一个自动生成    第二个过程描述   第三个过程名
    public static String insertSql = "insert into ngbossbk.t_proc_dict (cnt_user, cnt_item, cnt_name, cellphone, add_date, item_desc,"+
                                  "is_valid, self_flag, db_name, is_mid, item_type, is_cycle_run)"+
                          "values('JTB', ?, ?,'13851537761', to_date('"+(new DateUtils()).getToday("yyyMMdd")+"','yyyymmdd'), ?," +
                                  "1, 0, 'njzw3', 0, 1, 1)";
    
    /*
     * 创建的临时表 clj_20180831_tmp1
     * sql语句:
     * select * from clj_20180831_tmp1 where upper(cnt_user) like '%JTB%' or upper(cnt_user) like '%DEV%' order by cnt_item desc;
     * 
     * select * from clj_20180831_tmp1 where upper(cnt_user) like '%JTB%' or upper(cnt_user) like '%DEV%' order by cnt_item desc for update;
     */
    
    /** 
     * @description 获取过程编号
     * @author 陈吕奖 2018-08-31
     * @return
     */ 
    public int getCntItem(){
        int cnt_item = db.queryForInt(cntItemSql);
        int sign = 0;
        do{
            cnt_item++;
            sign = db.queryForInt(cntItemExistsSql, cnt_item);
        }while(sign >= 1);
        
        return cnt_item;
    }
    
    /** 
     * @description 添加一个过程项
     * @author 陈吕奖 2018-08-31
     * @param cntName   过程描述
     * @param itemDesc  过程名
     */
    @DataSource(name = DBContextHolder.zw3)
    public void addProItem(String cntName, String itemDesc){
        System.out.println(StringUtils.getSql(insertSql, getCntItem(), cntName, itemDesc));
        int i = db.update(insertSql, getCntItem(), cntName, itemDesc);
        if(i > 0)
            System.out.println("新增成功");
    }
}

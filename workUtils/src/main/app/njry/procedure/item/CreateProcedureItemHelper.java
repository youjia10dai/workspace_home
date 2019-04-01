/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.app.njry.procedure.item;

import main.helper.BaseHelper;
import main.spring.database.aspect.DataSource;
import main.spring.database.multDataSource.DBContextHolder;
import main.utils.common.DateUtils;
import main.utils.common.StringUtils;
import org.springframework.stereotype.Component;

/** 
 * @description ����������İ�����
 * ʹ��@tonjzw3�ķ�ʽ��
 * jtb.clj_20180831_tmp1@tonjzw3 ��ʱ����������
 * ����
 * 1.�����ܴ���Ȩ�޵�����
 *   ���:�����������ݿ�
 * @author ������
 * @date 2018-07-17 
 */
@Component("createProItem")
public class CreateProcedureItemHelper extends BaseHelper{

    //��ȡ���Ĺ�����,�����Ͽ���ʹ��
    public static String cntItemSql = "select max(cnt_item) from ngbossbk.t_proc_dict where (upper(cnt_user) like '%JTB%' or upper(cnt_user) like '%DEV%') and cnt_item not in (3001011,3000905)";
    
    //�����жϹ������Ƿ���ڵ�sql
    public static String cntItemExistsSql = "select count(1) from ngbossbk.t_proc_dict where cnt_item = ?";
    
    //3���ʺ�     ��һ���Զ�����    �ڶ�����������   ������������
    public static String insertSql = "insert into ngbossbk.t_proc_dict (cnt_user, cnt_item, cnt_name, cellphone, add_date, item_desc,"+
                                  "is_valid, self_flag, db_name, is_mid, item_type, is_cycle_run)"+
                          "values('JTB', ?, ?,'13851537761', to_date('"+(new DateUtils()).getToday("yyyMMdd")+"','yyyymmdd'), ?," +
                                  "1, 0, 'njzw3', 0, 1, 1)";
    
    /*
     * ��������ʱ�� clj_20180831_tmp1
     * sql���:
     * select * from clj_20180831_tmp1 where upper(cnt_user) like '%JTB%' or upper(cnt_user) like '%DEV%' order by cnt_item desc;
     * 
     * select * from clj_20180831_tmp1 where upper(cnt_user) like '%JTB%' or upper(cnt_user) like '%DEV%' order by cnt_item desc for update;
     */
    
    /** 
     * @description ��ȡ���̱��
     * @author ������ 2018-08-31
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
     * @description ���һ��������
     * @author ������ 2018-08-31
     * @param cntName   ��������
     * @param itemDesc  ������
     */
    @DataSource(name = DBContextHolder.zw3)
    public void addProItem(String cntName, String itemDesc){
        System.out.println(StringUtils.getSql(insertSql, getCntItem(), cntName, itemDesc));
        int i = db.update(insertSql, getCntItem(), cntName, itemDesc);
        if(i > 0)
            System.out.println("�����ɹ�");
    }
}

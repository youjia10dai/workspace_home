/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.helper.database.column;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.helper.BaseHelper;
import org.springframework.stereotype.Component;


/** 
 * @description ��ȡ�ֶ���ϸ��Ϣ������
 * @author ������
 * @date 2018-06-29 
 */
@Component
public class ColumnHelper extends BaseHelper{

    /** 
     * @description ������ֶ���Ϣ��װ��Field���϶���,ֻ��ȡ���ֶε����ֺ�����
     *             (�����Ҫ��ȡ������ֶ���Ϣ,�޸�Field����,�����ڸ���ķ��������)
     * @author ������ 2018-07-17
     * @param tableName
     * @return
     */ 
    public List<Column> getFieldInfoByTableName(String tableName){
        List<Column> list = new ArrayList<Column>();
        String sql = "select * from "+tableName;
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(sql);
            ResultSetMetaData data = rs.getMetaData();
            for (int i = 1; i <= data.getColumnCount(); i++) {
                list.add(new Column(data.getColumnName(i).toLowerCase(),data.getColumnTypeName(i).toLowerCase()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}

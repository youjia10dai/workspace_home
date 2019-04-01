/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
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
 * @description 获取字段详细信息工具类
 * @author 陈吕奖
 * @date 2018-06-29 
 */
@Component
public class ColumnHelper extends BaseHelper{

    /** 
     * @description 将表的字段信息封装成Field集合对象,只获取了字段的名字和类型
     *             (如果想要获取更多的字段信息,修改Field对象,并且在该类的方法中添加)
     * @author 陈吕奖 2018-07-17
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

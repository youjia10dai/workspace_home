package main.source.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** 
 * @description Java�����洢����
 * @author ������
 * @date 2018-10-19 
 */  
public class Java�����洢���� {
    Connection con;
    Statement state;
    ResultSet rs;
    CallableStatement cs;//���ô洢����ʹ�õĽӿ� 
    String url = "jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=test";
    String user = "sa";
    String password = "";

    public void connectSQL() {

        try {
            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
        }
        catch (ClassNotFoundException e) {
            // TODO �Զ����� catch �� 
            e.printStackTrace();
        }

        try {
            con = DriverManager.getConnection(url, user, password);
            state = con.createStatement();

            //�����洢����SQL��� 
            String createProcedure = " create procedure SHOW_SUPPLIERS " + "as "
                    + " select SUPPLIERS.SUP_NAME,COFFEES.COF_NAME " + "from suppliers,coffees"
                    + "where suppliers.sup_id = coffees.sup_id " + "order by sup_name";
            //�����洢���� 
            state.executeUpdate("USE TEST");
            state.executeUpdate(createProcedure);

            //���ô洢���� 
            cs = con.prepareCall("{call SHOW_SUPPLIERS}");//����һ�� CallableStatement �������������ݿ�洢���� 
            //���ص��õĽ���� 
            rs = cs.executeQuery();

            //������ 
            System.out.println("SUPPLIERS.SUP_NAME            COFFEES.COF_NAME");
            while(rs.next()) {
                String sup_name = rs.getString(1);
                String coffees_name = rs.getString(2);
                System.out.println(sup_name + "      " + coffees_name);
            }
            con.close();
            state.close();
        }
        catch (SQLException e) {
            // TODO �Զ����� catch �� 
            e.printStackTrace();
        }
    }
}

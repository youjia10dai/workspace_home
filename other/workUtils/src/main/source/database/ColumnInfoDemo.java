package main.source.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import main.spring.database.aspect.DataSource;
import main.spring.database.multDataSource.DBContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;



/** 
 * @description ��ȡ�����ֶ���Ϣ(ȫ)
 * @author ������
 * @date 2018-06-28 
 */  
@Component("fieldInfo")
@Lazy
@DataSource(name = DBContextHolder.zw3)
public class ColumnInfoDemo {
    
//    private DBHelper db;
    
    private Connection con;

    /**
	 * �ṩ�����Զ����ɸ������
	 * 
	 * @throws Exception
	 */
	public void main() throws Exception {
//		System.out.println(DBUtil.getUpdateSqlByTableName("cost"));
		test(con,"select * from t_ryyf_3000407_lltc_now_mid");
	}

	private void test(Connection conn, String sql) {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery(sql);
			ResultSetMetaData data = rs.getMetaData();
			for (int i = 1; i <= data.getColumnCount(); i++) {
				// ��������е���Ŀ��ʵ������
				int columnCount = data.getColumnCount();
				// ���ָ���е�����
				String columnName = data.getColumnName(i);
				// ���ָ���е���ֵ
				int columnType = data.getColumnType(i);
				// ���ָ���е�����������
				String columnTypeName = data.getColumnTypeName(i);
				// ���ڵ�Catalog����
				String catalogName = data.getCatalogName(i);
				// ��Ӧ�������͵���
				String columnClassName = data.getColumnClassName(i);
				// �����ݿ������͵�����ַ�����
				int columnDisplaySize = data.getColumnDisplaySize(i);
				// Ĭ�ϵ��еı���
				String columnLabel = data.getColumnLabel(i);
				// ����е�ģʽ
				String schemaName = data.getSchemaName(i);
				// ĳ�����͵ľ�ȷ��(���͵ĳ���)
				int precision = data.getPrecision(i);
				// С������λ��
				int scale = data.getScale(i);
				// ��ȡĳ�ж�Ӧ�ı���
				String tableName = data.getTableName(i);
				// �Ƿ��Զ�����
				boolean isAutoInctement = data.isAutoIncrement(i);
				// �����ݿ����Ƿ�Ϊ������
				boolean isCurrency = data.isCurrency(i);
				// �Ƿ�Ϊ��
				int isNullable = data.isNullable(i);
				// �Ƿ�Ϊֻ��
				boolean isReadOnly = data.isReadOnly(i);
				// �ܷ������where��
				boolean isSearchable = data.isSearchable(i);
				System.out.println(columnCount);
				System.out.println("�����" + i + "���ֶ�����:" + columnName);
				System.out.println("�����" + i + "������,����SqlType�еı��:"
						+ columnType);
				System.out.println("�����" + i + "������������:" + columnTypeName);
				System.out.println("�����" + i + "���ڵ�Catalog����:" + catalogName);
				System.out.println("�����" + i + "��Ӧ�������͵���:" + columnClassName);
				System.out.println("�����" + i + "�����ݿ������͵�����ַ�����:"
						+ columnDisplaySize);
				System.out.println("�����" + i + "��Ĭ�ϵ��еı���:" + columnLabel);
				System.out.println("�����" + i + "��ģʽ:" + schemaName);
				System.out.println("�����" + i + "���͵ľ�ȷ��(���͵ĳ���):" + precision);
				System.out.println("�����" + i + "С������λ��:" + scale);
				System.out.println("�����" + i + "��Ӧ�ı���:" + tableName);
				System.out.println("�����" + i + "�Ƿ��Զ�����:" + isAutoInctement);
				System.out.println("�����" + i + "�����ݿ����Ƿ�Ϊ������:" + isCurrency);
				System.out.println("�����" + i + "�Ƿ�Ϊ��:" + isNullable);
				System.out.println("�����" + i + "�Ƿ�Ϊֻ��:" + isReadOnly);
				System.out.println("�����" + i + "�ܷ������where��:" + isSearchable);
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
	}
	
   public Connection getCon() {
       return con;
   }
   @Autowired
   public void setCon(Connection con) {
       this.con = con;
   }
}

package sutdy.googleguava;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * google guava ����ʹ�ò���
 */
public class TableTest {
	//�������� HashMap<R, HashMap<C, V>>ʵ�֣�
	//    		           �� �����ظ�        �� ���ظ�      ֵ
	//  ���һ(һ�ж�Ӧһ��, һ�п��Զ�Ӧ����)
	public static Table<String, Operator, Object> conditionTable = HashBasedTable.create();
	static{
		//��ʼ������
        conditionTable.put("name1", Operator.LIKE, "name");
        conditionTable.put("type1", Operator.EQ, "type");
        conditionTable.put("applyType1", Operator.EQ, "applyType");
        conditionTable.put("isSystemApp1", Operator.EQ, "isSystemApp");
        conditionTable.put("regionName1", Operator.EQ, "regionName");
        conditionTable.put("createUserId1", Operator.EQ, "createUserId");
        conditionTable.put("corpId1", Operator.EQ, "corpId1");
        conditionTable.put("corpId1", Operator.EQ, "corpId");
	}
	
	/**
	 *	��ȡ���е���ֵ,ȥ��
	 */
	@Test
	public void getAllColumns() {
        Set<Operator> columnKeySet = conditionTable.columnKeySet();
        for (Operator operator : columnKeySet) {
			System.out.println(operator);
		}
	}
	
	/**
	 * ��ȡ���е���ֵ,ȥ��
	 */
	@Test
	public void getAllRows(){
        Set<String> rowKeySet = conditionTable.rowKeySet();
        for (String string : rowKeySet) {
			System.out.println(string);
		}
	}

	/**
	 * �����л�ȡ �к�����(������Map,���һ��key�ж��ֵ,�Ժ�ӵ�����Ϊ׼)
	 */
	@Test
	public void getRowAndValue(){
		Map<String, Object> column = conditionTable.column(Operator.EQ);
		System.out.println(column);
	}
	
	@Test
	public void getColumnAndValue(){
		Map<Operator, Object> row = conditionTable.row("applyType");
		System.out.println(row);
	}
}

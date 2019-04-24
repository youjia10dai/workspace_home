package sutdy.googleguava;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * google guava 集合使用测试
 */
public class TableTest {
	//本质上用 HashMap<R, HashMap<C, V>>实现；
	//    				 行 不可重复    列 可重复      值
	//  多对一(一行对应一列, 一列可以对应多行)
	public static Table<String, Operator, Object> conditionTable = HashBasedTable.create();
	static{
		//初始化数据
        conditionTable.put("name", Operator.LIKE, "name");
        conditionTable.put("type", Operator.EQ, "type");
        conditionTable.put("applyType", Operator.EQ, "applyType");
        conditionTable.put("isSystemApp", Operator.EQ, "isSystemApp");
        conditionTable.put("regionName", Operator.EQ, "regionName");
        conditionTable.put("createUserId", Operator.EQ, "createUserId");
        conditionTable.put("corpId", Operator.EQ, "corpId1");
        conditionTable.put("corpId", Operator.EQ, "corpId");
	}
	
	/**
	 *	获取所有的列值,去重
	 */
	@Test
	public void getAllColumns() {
        Set<Operator> columnKeySet = conditionTable.columnKeySet();
        for (Operator operator : columnKeySet) {
			System.out.println(operator);
		}
	}
	
	/**
	 * 获取所有的行值,去重
	 */
	@Test
	public void getAllRows(){
        Set<String> rowKeySet = conditionTable.rowKeySet();
        for (String string : rowKeySet) {
			System.out.println(string);
		}
	}

	/**
	 * 根据列获取 行和内容(正常的Map,如果一个key有多个值,以后加的数据为准)
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

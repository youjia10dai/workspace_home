package sutdy.fastjson.json.解析json文件.model;

import java.util.List;

import org.apache.commons.collections4.map.LinkedMap;

public class Query {
	private String id;
	private String key;
	private String tableName;
	private String className;
	private List<Column> columns;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// 这里省略部分getter与setter方法

	public String getKey() {
		return key;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		return "Query [id=" + id + ", key=" + key + ", tableName=" + tableName + ", className=" + className
				+ ", columns=" + columns + "]";
	}

}
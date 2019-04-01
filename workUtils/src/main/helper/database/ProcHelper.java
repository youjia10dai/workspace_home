package main.helper.database;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.core.SqlOutParameter;

/** 
 * @description 执行存储过程的帮助类
 * @author 陈吕奖
 * @date 2018-06-19 
 */  
public class ProcHelper extends StoredProcedure
{
	HashMap<String, Object> map = new HashMap<String, Object>();
	
	/**
	 * @description 构造方法
	 * @param dataSource 数据源
	 * @param proName 存储过程名称
	 */
	public ProcHelper(DataSource dataSource,String proName)
	{
		super();
		setDataSource(dataSource);
		setSql(proName);
	}
	
	public void setValue(String key, Object obj)
	{
		map.put(key, obj);
	}

	/** 
	 * @description 执行存储过程
	 * @author 陈吕奖 2018-06-19
	 * @return
	 */ 
	public Map<String, Object> execute()
	{
		try{
			if (this.getSql() == null || this.getSql().equals(""))
				return null;
			this.compile();
			return execute(map);
		}catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, Object>();
		}
	}

	/** 
	 * @description 设置存储过程的参数,输入的值  in
	 * @author 陈吕奖 2018-06-19
	 * @param param
	 */ 
	public void setVarcharParam(String param)
	{
		this.declareParameter(new SqlParameter(param, Types.VARCHAR));
	}

	public void setDoubleParam(String param)
	{
		this.declareParameter(new SqlParameter(param, Types.DOUBLE));
	}

	public void setIntegerParam(String param)
	{
		this.declareParameter(new SqlParameter(param, Types.INTEGER));
	}

	/** 
	 * @description 设置存储过程的参数,输出的值   out
	 * @author 陈吕奖 2018-06-19
	 * @param param
	 */ 
	public void setVarcharOutParam(String param)
	{
		this.declareParameter(new SqlOutParameter(param, Types.VARCHAR));
	}

	public void setDoubleOutParam(String param)
	{
		this.declareParameter(new SqlOutParameter(param, Types.DOUBLE));
	}

	public void setIntegerOutParam(String param)
	{
		this.declareParameter(new SqlOutParameter(param, Types.INTEGER));
	}
}

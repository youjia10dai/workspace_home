package com.royasoft.errorReport.api.interfaces;

import java.util.Map;

import com.royasoft.errorReport.api.vo.ErrorInfoVo;
import com.royasoft.errorReport.impl.entity.ErrorInfo;

/**
 * 异常上报接口
 * 
 * @author chenlj
 * 
 * @Date 2019-04-08
 *
 */
public interface ErrorReportInterface {

	/**
	 * 上报异常信息
	 * @param errorInfoVo
	 */
	public ErrorInfo add(ErrorInfoVo errorInfoVo);
	
	/**
	 * 更新异常信息
	 * @param errorInfoVo
	 * @return
	 */
	public Integer update(ErrorInfoVo errorInfoVo);
	
	/**
	 * 获取所有的版本信息
	 * @return
	 */
	public String[] getAllVersions();
	
    /**
     * 查询上报异常信息,分页显示<br>
     * 
     * @param page 需要显示的页码
     * @param rows 每页显示多少条
     * @param conditions 查询条件 以EQ_(等于), LIKE_(模糊查询), GT_(大于), LT_(小于), GTE_(大于等于), LTE_(小于等于)开头 时间类型 开始时间 以start_time_开头 结束时间以end_time_开头 时间格式不做要求
     * 
     * @param sortMap 排序的map key为排序的字段 value为true 升序 false 降序
     * @return
     */
    public Map<String, Object> findErrorInfoByPage(int page, int rows, Map<String, Object> conditions, Map<String, Boolean> sortMap);
	
}

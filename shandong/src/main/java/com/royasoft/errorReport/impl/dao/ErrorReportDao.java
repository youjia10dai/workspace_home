package com.royasoft.errorReport.impl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.royasoft.errorReport.impl.entity.ErrorInfo;

public interface ErrorReportDao extends PagingAndSortingRepository<ErrorInfo, Integer>, JpaSpecificationExecutor<ErrorInfo>{

	@Query(value = "UPDATE ErrorInfo SET remark =?1, state =?2  WHERE id =?3")
	@Modifying
	public int updateByid(String remark, String state, Integer id);
	
	//查询部分字段
	@Query(value ="select distinct version "
		       	+ "  from vwt_error_info ",nativeQuery = true)
	public List<String> getAllVersions();
	
}
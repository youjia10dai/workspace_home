package com.royasoft.errorReport.impl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.royasoft.errorReport.api.interfaces.ErrorReportInterface;
import com.royasoft.errorReport.api.vo.ErrorInfoVo;
import com.royasoft.errorReport.impl.dao.ErrorReportDao;
import com.royasoft.errorReport.impl.entity.ErrorInfo;
import com.royasoft.util.DateFormat;
import com.royasoft.util.DynamicSpecifications;
import com.royasoft.util.SearchFilter;

/**
 * 日志上报
 * 
 * @author chenlj
 * @date 2019-04-08
 */
@Transactional
@Service
public class ErrorReportService implements ErrorReportInterface {

	private final Logger logger = LoggerFactory.getLogger(ErrorReportService.class);

	@Autowired
	private ErrorReportDao errorReportdao;

	@Override
	public ErrorInfo add(ErrorInfoVo errorInfoVo) {
		logger.debug("异常上报添加操作, VO{}", JSON.toJSONString(errorInfoVo));
		if (errorInfoVo == null) {
			logger.error("待添加异常上报数据为空");
			return null;
		}
		try {
			ErrorInfo errorInfo = errorReportdao.save(transVoToEntity(errorInfoVo));
			if (errorInfo == null) {
				logger.error("异常上报失败");
				return null;
			}
			logger.debug("异常上报成功");
			return errorInfo;
		} catch (Exception e) {
			logger.error("异常上报出错信息{}", e);
		}
		return null;
	}

	@Override
	public Integer update(ErrorInfoVo errorInfoVo) {
		logger.debug("异常上报修改操作, VO{}", JSON.toJSONString(errorInfoVo));
		if (errorInfoVo == null) {
			logger.error("待更新异常上报数据为空");
			return null;
		}
		try {
			int count = errorReportdao.updateByid(errorInfoVo.getRemark(), errorInfoVo.getState(), errorInfoVo.getId());
			if (count != 1) {
				logger.error("异常上报更新条数有误,更新的条数为{}", count);
				return null;
			}
			logger.debug("异常上报更新成功");
			return count;
		} catch (Exception e) {
			logger.error("异常上报更新出错信息{}", e);
		}
		return null;
	}

	@Override
	public String[] getAllVersions() {
		logger.debug("获取所有的版本信息");
		try {
			List<String> allVersions = errorReportdao.getAllVersions();
			if (allVersions == null)
				return new String[] {};
			logger.debug("获取所有版本信息成功");
			return allVersions.toArray(new String[] {});
		} catch (Exception e) {
			logger.error("异常上报获取所有版本出错,出错信息为{}", e);
		}
		return null;
	}

	@Override
	public Map<String, Object> findErrorInfoByPage(int page, int rows, Map<String, Object> conditions,
			Map<String, Boolean> sortMap) {
		logger.debug("分页查询日志列表, page{}, rows{},conditions{},sortMap{}", page, rows, conditions, sortMap);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 对conditions中的日期类型 format
			conditions = DateFormat.formatDate(conditions);

			PageRequest pageRequest = buildPageRequest(page, rows, sortMap);

			Map<String, SearchFilter> filters = SearchFilter.parse(conditions);

			Specification<ErrorInfo> spec = DynamicSpecifications.bySearchFilter(filters.values(), ErrorInfo.class);
			Page<ErrorInfo> pages = errorReportdao.findAll(spec, pageRequest);
			List<ErrorInfo> list = pages.getContent();

			map.put("total", pages.getTotalElements());
			map.put("content", transEntityToVo(list));
			return map;
		} catch (Exception e) {
			logger.error("分页查询日志列表异常", e);
		}
		return null;
	}

	/**
	 * 构建分页请求
	 * 
	 * @param pageNumber
	 * @param pagzSize
	 * @param sortType
	 * @return
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, Map<String, Boolean> sortMap) {
		Sort sort = null;
		if (sortMap == null)
			sort = new Sort(Direction.ASC, "id");
		else {
			Iterator<String> keys = sortMap.keySet().iterator();
			List<Order> orderList = new ArrayList<Sort.Order>();
			while (keys.hasNext()) {
				String key = keys.next();
				boolean value = Boolean.valueOf(sortMap.get(key));
				orderList.add(new Order(value == true ? Direction.ASC : Direction.DESC, key));
			}
			sort = new Sort(orderList);
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);

	}

	/**
	 * 将实体集合转换成VO集合
	 * 
	 * @param entitys
	 * @return
	 */
	public List<ErrorInfoVo> transEntityToVo(List<ErrorInfo> entitys) {
		List<ErrorInfoVo> Vos = new ArrayList<ErrorInfoVo>();
		for (ErrorInfo entiry : entitys) {
			Vos.add(transEntityToVo(entiry));
		}
		return Vos;
	}

	/**
	 * 将实体对象转换成VO对象
	 * 
	 * @param entity
	 * @return
	 */
	public ErrorInfoVo transEntityToVo(ErrorInfo entity) {
		ErrorInfoVo vo = new ErrorInfoVo();
		try {
			BeanUtils.copyProperties(entity, vo);
		} catch (Exception e) {
			logger.error("异常上报实体对象转换VO异常", e);
		}
		return vo;
	}

	/**
	 * 将Vo对象转换成实体对象
	 * 
	 * @param vo
	 * @return
	 */
	public ErrorInfo transVoToEntity(ErrorInfoVo vo) {
		ErrorInfo entity = new ErrorInfo();
		try {
			BeanUtils.copyProperties(vo, entity);
		} catch (BeansException e) {
			logger.error("异常上报VO转换实体对象异常", e);
		}
		return entity;
	}

}
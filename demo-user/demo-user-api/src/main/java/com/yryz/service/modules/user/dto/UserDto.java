package com.yryz.service.modules.user.dto;


import com.yryz.service.modules.user.entity.User;

/**
 * @ClassName: UserDto
 * @Description: UserDto
 * @author pengnian
 * @date 2017-09-01 11:40:43
 *
 */
public class UserDto extends User {
	/**
	 * 开始时间
	 */
	private String startDate;

	/**
	 * 结束时间
	 */
	private String endDate;

	/*
	 * 排序方式
	 */
	private String orderBy;

	/*
	 * 分页-当前第几页
	 */
	private Integer pageNo = 1;

	/*
	 * 分页-每页显示记录数量
	 */
	private Integer pageSize = 10;

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
}

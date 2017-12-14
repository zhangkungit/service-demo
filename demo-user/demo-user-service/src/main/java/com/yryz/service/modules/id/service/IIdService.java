package com.yryz.service.modules.id.service;

public interface IIdService {
	
	/**
	 * 生成订单ID
	 * 
	 * @return
	 * @param type
	 */
	public String getId(String type);
	
	/**
	 * 生成用户ID
	 * 
	 * @return
	 */
	public String getUserId();

}

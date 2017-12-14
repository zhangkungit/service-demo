package com.yryz.service.modules.id.api;

/**
 * Dubbo Service(id) API
 * @author
 */
public interface IdAPI {
	
	/**
	 * 生成订单ID
	 * 
	 * @return
	 */
	public String getId(String type);
	
	/**
	 * 生成用户ID
	 * 
	 * @return
	 */
	public String getUserId();

}

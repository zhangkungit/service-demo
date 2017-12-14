package com.yryz.service.modules.user.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * 
  * @ClassName: User
  * @Description: 用户表实体类
  * @author zhangkun
  * @date 2017-11-30 17:12:28
  *
 */
public class User extends GenericEntity {
	
	/**
	 * 用户id
	 */	 
    private  String userId;
    
	/**
	 * 账号
	 */	 
    private  String account;
    
	/**
	 * 密码
	 */	 
    private  String password;
    
	/**
	 * 用户真实姓名
	 */	 
    private  String userName;
    
	/**
	 * 用户昵称
	 */	 
    private  String nickName;
    
	/**
	 * 删除标记（0:正常，1:删除）
	 */	 
    private  Integer delFlag;
    

	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
		
	public String getAccount() {
		return this.account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
		
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
		
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
		
	public String getNickName() {
		return this.nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
		
	public Integer getDelFlag() {
		return this.delFlag;
	}
	
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
		
}
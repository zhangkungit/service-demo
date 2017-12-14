package com.yryz.service.modules.id.entity;

import java.io.Serializable;

public class PrimaryKey implements Serializable {

	private static final long serialVersionUID = -1112697843802058950L;
	/**
	 * 主键名称
	 */
	private String primaryName;
	/**
	 * 当前值
	 */
	private Long currentValue;
	/**
	 * 步长
	 */
	private Long step;

	/**
	 * 主键名称
	 */
	public String getPrimaryName() {
		return primaryName;
	}

	/**
	 * 主键名称
	 */
	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
	}

	/**
	 * 当前值
	 */
	public Long getCurrentValue() {
		return currentValue;
	}

	/**
	 * 当前值
	 */
	public void setCurrentValue(Long currentValue) {
		this.currentValue = currentValue;
	}

	/**
	 * 步长
	 */
	public Long getStep() {
		return step;
	}

	/**
	 * 步长
	 */
	public void setStep(Long step) {
		this.step = step;
	}

}

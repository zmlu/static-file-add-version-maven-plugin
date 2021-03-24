package com.echemi.maven.plugin.entity;

import lombok.Data;

/**
 * @author jacob
 * created in  2021/3/13 11:05
 * modified By:
 */
@Data
public class DocLabel {
	/**
	 * 从indexPos位置开始检查标签
	 */
	private int indexPos;
	/**
	 * 标签开始标识
	 */
	private String startSign;
	/**
	 * 标签开始位置
	 */
	private int startSignPos;
	/**
	 * 标签配置源标识
	 */
	private String sourceSign;
	/**
	 * 标签配置源位置
	 */
	private int sourceSignPos;
	/**
	 * 标签结束标识
	 */
	private String endSign;
	/**
	 * 标签结束位置
	 */
	private int endSignPos;
	/**
	 * 是否找到此标签
	 */
	private boolean hasFind;
	
	public DocLabel(int indexPos, String startSign, String sourceSign, String endSign) {
		this.indexPos = indexPos;
		this.startSign = startSign;
		this.sourceSign = sourceSign;
		this.endSign = endSign;
	}
}

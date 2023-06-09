package com.echemi.maven.plugin.entity;

import lombok.Data;

import java.io.File;

/**
 * @author jacob
 * created in  2021/3/13 15:28
 * modified By:
 */
@Data
public class FileInfo {

	/**
	 * 文件类型
	 **/
	private String fileType;
	/**
	 * 文件名称
	 **/
	private String fileName;
	/**
	 * 输出最终文件名
	 **/
	private String finalFileName;

	/**
	 * 文件对象
	 **/
	private File file;

	/**
	 * 文件版本号
	 **/
	private String fileVersion;

	/**
	 * 文件hash值
	 **/
	private String fileHashKey;

	/**
	 * 相对文件路径  去除了文件webRoot 和全部前缀路径
	 **/
	private String relativelyFilePath;

	/**
	 * 文件是否需要被重命名
	 **/
	private boolean needRename = false;
	private long lastModified = System.currentTimeMillis();
}

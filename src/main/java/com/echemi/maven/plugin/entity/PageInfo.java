package com.echemi.maven.plugin.entity;

import java.io.File;

/**
 * @author jacob
 * created in  2021/3/13 16:06
 * modified By:
 */
public class PageInfo {
	private File file;
	/**
	 * 输出文件位置
	 */
	private File outFile;
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public File getOutFile() {
		return outFile;
	}
	
	public void setOutFile(File outFile) {
		this.outFile = outFile;
	}
}

package com.echemi.maven.plugin.constant;

/**
 * @author jacob
 * created in  2021/3/13 14:38
 * modified By:
 */
public enum FileTypeEnum {
	JS("JS"),
	CSS("CSS"),
	IMAGE("IMAGE");
	private final String type;
	
	FileTypeEnum(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}

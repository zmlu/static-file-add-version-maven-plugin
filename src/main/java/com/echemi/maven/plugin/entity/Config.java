package com.echemi.maven.plugin.entity;

import com.alibaba.fastjson.JSONObject;
import com.echemi.maven.plugin.constant.MethodEnum;
import com.echemi.maven.plugin.utils.JsonUtils;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jacob
 * created in  2021/3/13 15:21
 * modified By:
 */
@Data
public class Config {
	@Getter
	private static JSONObject selfConfig = JsonUtils.readJsonFile("config.json");
	/**
	 * 需要替换内容的后缀
	 */
	private List<String> suffix;
	/**
	 * 域名
	 * JS和CSS采用域名配置时的连接，非必填项
	 */
	private List<String> domains;
	/**
	 * js 使用方法
	 */
	private MethodEnum method;
	/**
	 * 版本号标签
	 **/
	private String versionLabel;
	/**
	 * 文件编码
	 **/
	private String sourceEncoding;
	/**
	 * 跳过文件名后缀(后缀之前的名称)
	 **/
	private String skipSuffix;
	/**
	 * 排除文件(暂时只支持根路径开始的，不支持正则)
	 **/
	private List<String> excludes;
	/**
	 * 包含文件(暂时只支持根路径开始的，不支持正则)
	 */
	private List<String> includes;
	/**
	 * 根目录名称
	 **/
	private String webRootName;
	/**
	 * 输出文件目录
	 */
	private String outDirRoot;
	/**
	 * 全局JS文件EL前缀
	 */
	private String cdnJsElName;
	/**
	 * 全局CSS文件EL前缀
	 */
	private String cdnCssElName;
	/**
	 * 全局Image文件EL前缀
	 */
	private String cdnImageElName;
	/**
	 * 全局EL前缀替换成的本地静态资源目录
	 */
	private String elNameIncludePath;
	/**
	 * 是否压缩输出文件
	 */
	private boolean compressOutput;
	
	private int versionLength;
	private boolean inName;
	
	public static ArrayList<String> getSuffixList() {
		ArrayList<String> rtnArray = new ArrayList<>();
		if (selfConfig != null) {
			JSONObject processFileType = selfConfig.getJSONObject("process_file_type");
			for (String fileType : processFileType.keySet()) {
				JSONObject fileTypeObj = processFileType.getJSONObject(fileType);
				String suffixes = fileTypeObj.getString("suffixes");
				rtnArray.addAll(Arrays.asList(suffixes.split(",")));
			}
		}
		return rtnArray;
	}
	
	
	public static void main(String[] args) {
		System.out.println(Config.getSelfConfig());
		System.out.println(Config.getSuffixList());
	}
}

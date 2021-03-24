package com.echemi.maven.plugin;

import com.echemi.maven.plugin.constant.MethodEnum;
import lombok.Data;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jacob
 * created in  2021/3/13 14:29
 * modified By:
 */
@Data
public abstract class BaseMojo extends AbstractMojo {
	
	/**
	 * 需要替换内容的后缀
	 */
	@Parameter(defaultValue = "html")
	protected List<String> suffix;
	
	/**
	 * 域名
	 * JS和CSS采用域名配置时的连接，非必填项
	 */
	@Parameter(defaultValue = "http://www.baidu.com")
	protected List<String> domains;
	
	/**
	 * js 使用方法
	 */
	@Parameter(defaultValue = "MD5_METHOD")
	protected MethodEnum method;
	
	/**
	 * 版本号标签
	 **/
	@Parameter(defaultValue = "v")
	protected String versionLabel;
	
	/**
	 * 文件编码
	 **/
	@Parameter(defaultValue = "UTF-8")
	protected String sourceEncoding;
	
	/**
	 * 跳过文件名后缀(后缀之前的名称)
	 **/
	@Parameter(defaultValue = "")
	protected String skipSuffix;
	
	/**
	 * 排除文件(暂时只支持根路径开始的，不支持正则)
	 **/
	@Parameter
	protected List<String> excludes = new ArrayList<>();
	
	/**
	 * 包含文件(暂时只支持根路径开始的，不支持正则)
	 */
	@Parameter
	protected List<String> includes = new ArrayList<>();
	
	/**
	 * 输出文件目录
	 */
	@Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
	protected File outputDir;
	
	/**
	 * 根目录名称
	 **/
	@Parameter(defaultValue = "${project.build.finalName}")
	protected String webRootName;
	
	/**
	 * 全局JS文件EL前缀
	 */
	@Parameter(defaultValue = "")
	protected String cdnJsElName;
	
	/**
	 * 全局CSS文件EL前缀
	 */
	@Parameter(defaultValue = "")
	protected String cdnCssElName;
	
	/**
	 * 全局Image文件EL前缀
	 */
	@Parameter(defaultValue = "")
	protected String cdnImageElName;
	
	/**
	 * 全局EL前缀替换成的本地静态资源目录
	 */
	@Parameter(defaultValue = "")
	protected String elNameIncludePath;
}

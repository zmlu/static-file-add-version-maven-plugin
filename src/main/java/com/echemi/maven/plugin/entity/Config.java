package com.echemi.maven.plugin.entity;

import com.echemi.maven.plugin.constant.MethodEnum;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.List;

/**
 * @author jacob
 * created in  2021/3/13 15:21
 * modified By:
 */
public class Config {
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
	
	private int versionLength;

	private boolean inName;

	public List<String> getSuffix() {
		return suffix;
	}

	public void setSuffix(List<String> suffix) {
		this.suffix = suffix;
	}

	public List<String> getDomains() {
		return domains;
	}

	public void setDomains(List<String> domains) {
		this.domains = domains;
	}

	public MethodEnum getMethod() {
		return method;
	}

	public void setMethod(MethodEnum method) {
		this.method = method;
	}

	public String getVersionLabel() {
		return versionLabel;
	}

	public void setVersionLabel(String versionLabel) {
		this.versionLabel = versionLabel;
	}

	public String getSourceEncoding() {
		return sourceEncoding;
	}

	public void setSourceEncoding(String sourceEncoding) {
		this.sourceEncoding = sourceEncoding;
	}

	public String getSkipSuffix() {
		return skipSuffix;
	}

	public void setSkipSuffix(String skipSuffix) {
		this.skipSuffix = skipSuffix;
	}

	public List<String> getExcludes() {
		return excludes;
	}

	public void setExcludes(List<String> excludes) {
		this.excludes = excludes;
	}

	public List<String> getIncludes() {
		return includes;
	}

	public void setIncludes(List<String> includes) {
		this.includes = includes;
	}

	public String getWebRootName() {
		return webRootName;
	}

	public void setWebRootName(String webRootName) {
		this.webRootName = webRootName;
	}

	public String getOutDirRoot() {
		return outDirRoot;
	}

	public void setOutDirRoot(String outDirRoot) {
		this.outDirRoot = outDirRoot;
	}

	public int getVersionLength() {
		return versionLength;
	}

	public void setVersionLength(int versionLength) {
		this.versionLength = versionLength;
	}

	public boolean isInName() {
		return inName;
	}

	public void setInName(boolean inName) {
		this.inName = inName;
	}

	public String getCdnJsElName() {
		return cdnJsElName;
	}

	public void setCdnJsElName(String cdnJsElName) {
		this.cdnJsElName = cdnJsElName;
	}

	public String getCdnCssElName() {
		return cdnCssElName;
	}

	public void setCdnCssElName(String cdnCssElName) {
		this.cdnCssElName = cdnCssElName;
	}

	public String getCdnImageElName() {
		return cdnImageElName;
	}

	public void setCdnImageElName(String cdnImageElName) {
		this.cdnImageElName = cdnImageElName;
	}

	public String getElNameIncludePath() {
		return elNameIncludePath;
	}

	public void setElNameIncludePath(String elNameIncludePath) {
		this.elNameIncludePath = elNameIncludePath;
	}
}

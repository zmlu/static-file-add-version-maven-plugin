package com.echemi.maven.plugin.support;

import com.alibaba.fastjson.JSONObject;
import com.echemi.maven.plugin.constant.Constants;
import com.echemi.maven.plugin.constant.FileTypeEnum;
import com.echemi.maven.plugin.constant.MethodEnum;
import com.echemi.maven.plugin.entity.Config;
import com.echemi.maven.plugin.entity.DocLabel;
import com.echemi.maven.plugin.entity.FileInfo;
import com.echemi.maven.plugin.entity.PageInfo;
import com.echemi.maven.plugin.utils.BaseUtils;
import com.echemi.maven.plugin.utils.DocUtils;
import com.echemi.maven.plugin.utils.StringUtils;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jacob
 * created in  2021/3/13 15:21
 * modified By:
 */
public abstract class AbstractProcessFactory implements ProcessFactory {
	protected final static int SPLIT_INDEX = 2;
	
	protected Log logger;
	protected Config config;
	protected Map<String, FileInfo> files;
	protected List<PageInfo> pages;
	protected String webapp;
	
	public AbstractProcessFactory(Config config) {
		this.config = config;
	}
	
	protected String getFileVersion(File file, MethodEnum method) {
		try {
			return BaseUtils.getFileHashKey(file, method.getMethod());
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
		return System.currentTimeMillis() + "";
	}
	
	protected int processVersion(PageInfo pageInfo, StringBuffer html, int index, List<FileInfo> processSuccessFiles, final FileTypeEnum fileType, final JSONObject fileTypeSettings) {
		final String startSign = fileTypeSettings.getString("start");
		final String sourceSign = fileTypeSettings.getString("src");
		final String endSign = fileTypeSettings.getString("end");
		final boolean withQuote = fileTypeSettings.getBooleanValue("with_quote");
		
		index = index < 0 ? 0 : index;
		DocLabel docLabel = new DocLabel(index, startSign, sourceSign, endSign);
		if (docLabel == null) {
			return -1;
		}
		int heardLength = docLabel.getStartSign().length();
		
		DocUtils.getDocLabelPosition(html, docLabel);
		if (!docLabel.isHasFind()) {
			if (index < html.length() && docLabel.getSourceSignPos() > 0) {
				return processVersion(pageInfo, html, docLabel.getStartSignPos() + heardLength + 1, processSuccessFiles, fileType, fileTypeSettings);
			} else {
				return -1;
			}
		}
		char[] cas = html.toString().toCharArray();
		if (withQuote) {
			char endChar = cas[docLabel.getSourceSignPos()];
			if (endChar != Constants.CHAR_SINGLE_QUOTE_MARK && endChar != Constants.CHAR_DOUBLE_QUOTE_MARK) {
				return processVersion(pageInfo, html, docLabel.getEndSignPos(), processSuccessFiles, fileType, fileTypeSettings);
			}
			index = docLabel.getSourceSignPos() - 1;
			index = index < 0 ? 0 : index;
			docLabel = new DocLabel(index, startSign, sourceSign, endSign);
			if (docLabel == null) {
				return -1;
			}
			docLabel.setStartSign(endChar + Constants.EMPTY_STR);
			docLabel.setSourceSign(endChar + Constants.EMPTY_STR);
			DocUtils.getDocLabelPosition(html, docLabel);
			
			if (!docLabel.isHasFind()) {
				return -1;
			}
			int length = docLabel.getSourceSignPos() - docLabel.getStartSignPos() - 2;
			if (length < 0) {
				return -1;
			}
			char[] links = new char[length];
			System.arraycopy(cas, docLabel.getStartSignPos() + 1, links, 0, length);
			String link = new String(links);
			if (!getUrlPar(link).contains(config.getVersionLabel() + "=")) {
				logger.debug("find " + fileType + " link:" + link);
				processLink(pageInfo, html, docLabel.getStartSignPos() - 1, docLabel.getSourceSignPos() - 1, link, fileType, processSuccessFiles);
			}
			return processVersion(pageInfo, html, docLabel.getEndSignPos(), processSuccessFiles, fileType, fileTypeSettings);
		} else {
			char startChar = cas[docLabel.getSourceSignPos()];
			char endChar = cas[docLabel.getEndSignPos()];
			index = docLabel.getSourceSignPos() - 1;
			index = index < 0 ? 0 : index;
			docLabel = new DocLabel(index, startSign, sourceSign, endSign);
			if (docLabel == null) {
				return -1;
			}
			docLabel.setStartSign(startChar + Constants.EMPTY_STR);
			docLabel.setSourceSign(endChar + Constants.EMPTY_STR);
			DocUtils.getDocLabelPosition(html, docLabel);
			
			int length = docLabel.getSourceSignPos() - docLabel.getStartSignPos() - 1;
			if (length < 0) {
				return -1;
			}
			char[] links = new char[length];
			System.arraycopy(cas, docLabel.getStartSignPos(), links, 0, length);
			String link = new String(links);
			if (!getUrlPar(link).contains(config.getVersionLabel() + "=")) {
				logger.debug("find " + fileType + " link:" + link);
				processLink(pageInfo, html, docLabel.getStartSignPos() - 1, docLabel.getSourceSignPos() - 1, link, fileType, processSuccessFiles);
			}
			return processVersion(pageInfo, html, docLabel.getSourceSignPos() - 1, processSuccessFiles, fileType, fileTypeSettings);
		}
	}
	
	private void processLink(PageInfo pageInfo, StringBuffer sb, final int start, int end, final String historyLink, final FileTypeEnum fileType, List<FileInfo> processSuccessFiles) {
		FileInfo fileInfo = null;
		StringBuilder fullLink = new StringBuilder();
		
		List<String> domains = config.getDomains();
		List<String> includes = config.getIncludes();
		if (historyLink.startsWith(Constants.HTTP_START_HEARD) || historyLink.startsWith(Constants.HTTPS_START_HEARD)) {
			for (String domain : domains) {
				fullLink.append(domain);
				String tempUrl = historyLink.replaceFirst(fullLink.toString(), Constants.EMPTY_STR);
				if (tempUrl.startsWith(Constants.STR_SLASH)) {
					tempUrl = tempUrl.replaceFirst(Constants.STR_SLASH, Constants.EMPTY_STR);
				}
				tempUrl = removeUrlPar(tempUrl);
				fileInfo = files.get(tempUrl);
				if (fileInfo != null) {
					break;
				}
			}
		} else {
			fullLink.append(historyLink);
			fullLink = new StringBuilder(removeUrlPar(fullLink.toString()));
			for (String include : includes) {
				int index = fullLink.indexOf(include, 0);
				if (index > 0) {
					fullLink.delete(0, index);
					break;
				}
			}
			if (fullLink.indexOf(Constants.STR_SLASH, 0) == 0) {
				fullLink.delete(0, 1);
			}
			
			String globalElEqualStaticPath = config.getElNameIncludePath();
			String globalElPrefix = "";
			switch (fileType) {
				case JS:
					globalElPrefix = config.getCdnJsElName();
					break;
				case CSS:
					globalElPrefix = config.getCdnCssElName();
					break;
				case IMAGE:
					globalElPrefix = config.getCdnImageElName();
					break;
			}
			//thymeleaf start
			// 情况1：判断中间含有 ${cdnEl}+'path'
			String pattern = "\\$\\{" + globalElPrefix + "}\\+'(.*?)'";
			Pattern p = Pattern.compile(pattern);
			Matcher matcher = p.matcher(fullLink.toString());
			if (matcher.find()) {
				String s = matcher.group(1);
				if (StringUtils.isNotEmpty(s)) {
					int sStart = fullLink.indexOf(s);
					int sEnd = sStart + s.length();
					int py = fullLink.toString().length() - sEnd;
					if (py >= 0) {
						fullLink = new StringBuilder("${" + globalElPrefix + "}" + s);
						end -= py;
					}
				}
			}
			// 情况2：判断中间含有 |${cdnEl}path|
			pattern = "\\|\\$\\{" + globalElPrefix + "}(.*?)\\|";
			p = Pattern.compile(pattern);
			matcher = p.matcher(fullLink.toString());
			if (matcher.find()) {
				String s = matcher.group(1);
				if (StringUtils.isNotEmpty(s)) {
					int sStart = fullLink.indexOf(s);
					int sEnd = sStart + s.length();
					int py = fullLink.toString().length() - sEnd;
					if (py >= 0) {
						fullLink = new StringBuilder("${" + globalElPrefix + "}" + s);
						end -= py;
					}
				}
			}
			//thymeleaf end
			if (StringUtils.isNotEmpty(globalElEqualStaticPath) && StringUtils.isNotEmpty(globalElPrefix)) {
				fullLink = new StringBuilder(fullLink.toString().replace("${" + globalElPrefix + "}", globalElEqualStaticPath));
			}
			if (fullLink.toString().startsWith("..")) {
				File parentFolder = new File(pageInfo.getFile().getParent());
				File b = new File(parentFolder, fullLink.toString());
				try {
					fullLink = new StringBuilder(b.getCanonicalPath().replace(webapp, ""));
					if (fullLink.indexOf(Constants.STR_SLASH, 0) == 0) {
						fullLink.delete(0, 1);
					}// may throw IOException
				} catch (Exception e) {
					logger.debug("cant get relative path: [" + fullLink + "] in file:[" + fileInfo.getFile().getAbsolutePath() + "]");
				}
			}
			fileInfo = files.get(fullLink.toString());
		}
		
		if (fileInfo != null && !Constants.EMPTY_STR.equals(fullLink.toString())) {
			insertVersion(sb, start, end, historyLink, fullLink.toString(), fileInfo, processSuccessFiles);
		}
	}
	
	private void insertVersion(StringBuffer sb, final int start, final int end, final String historyLink, String fullLink, FileInfo fileInfo, List<FileInfo> processSuccessFiles) {
		if (fileInfo != null) {
			logger.debug("process link:" + historyLink + " : " + fullLink);
			String versionStr = "";
			if (!checkIsSkip(fileInfo, config)) {
				versionStr = getVersionStr(fileInfo, config.isInName(), historyLink);
			}
			processSuccessFiles.add(fileInfo);
			if (config.isInName()) {
				int fileNameLength = fileInfo.getFileName().length();
				int parLength = 0;
				String par;
				if (historyLink.indexOf(Constants.STR_QUESTION_MARK) > 0) {
					par = getUrlPar(historyLink);
					parLength = par.length();
					parLength++;
				}
				sb.replace(end - fileNameLength - parLength, end, Constants.EMPTY_STR);
				sb.insert(end - fileNameLength - parLength, versionStr);
			} else {
				sb.insert(end, versionStr);
			}
			
		}
		
	}
	
	private String getVersionStr(FileInfo fileInfo, final boolean isInName, final String historyLink) {
		String param = "";
		if (historyLink.indexOf(Constants.STR_QUESTION_MARK) > 0) {
			param = getUrlPar(historyLink);
		}
		String version = fileInfo.getFileVersion();
		if (config.getVersionLength() >= 1 && version.length() > config.getVersionLength()) {
			version = version.substring(0, config.getVersionLength());
		}
		String versionStr;
		if (isInName) {
			versionStr = version + Constants.STR_DOT + fileInfo.getFileType();
			fileInfo.setFinalFileName(versionStr);
			if (StringUtils.isNotEmpty(param)) {
				versionStr += Constants.STR_QUESTION_MARK + param;
			}
		} else {
			if (StringUtils.isEmpty(param)) {
				versionStr = Constants.STR_QUESTION_MARK + config.getVersionLabel() + "=" + version;
			} else {
				versionStr = Constants.STR_AND_MARK + config.getVersionLabel() + "=" + version;
			}
		}
		return versionStr;
	}
	
	
	private boolean checkIsSkip(final FileInfo fileInfo, final Config config) {
		if (StringUtils.isNotEmpty(config.getSkipSuffix())) {
			if (fileInfo.getFileName().contains(config.getSkipSuffix() + Constants.STR_DOT + fileInfo.getFileType())) {
				logger.debug("The suffix is " + config.getSkipSuffix() + " ,not processed:" + fileInfo.getFileName());
				return true;
			}
		}
		List<String> excludes = config.getExcludes();
		if (excludes != null && (!excludes.isEmpty()) && BaseUtils.checkStrIsInList(excludes, fileInfo.getRelativelyFilePath(), true)) {
			logger.debug("The file  is not processed:" + fileInfo.getFileName());
			return true;
		}
		return false;
	}
	
	private String getUrlPar(String url) {
		if (url.indexOf(Constants.STR_QUESTION_MARK) > 0) {
			String[] split = url.split(Constants.STR_QUESTION_MARK_REGEX);
			if (split.length == SPLIT_INDEX) {
				return split[1];
			} else {
				return Constants.EMPTY_STR;
			}
		}
		return Constants.EMPTY_STR;
		
	}
	
	private String removeUrlPar(String url) {
		if (url.indexOf(Constants.STR_QUESTION_MARK) > 0) {
			String[] split = url.split(Constants.STR_QUESTION_MARK_REGEX);
			if (split.length == SPLIT_INDEX) {
				url = split[0];
			}
		}
		return url;
	}
	
	@Override
	public void buildLoggerFactory(Log logger) {
		this.logger = logger;
	}
}

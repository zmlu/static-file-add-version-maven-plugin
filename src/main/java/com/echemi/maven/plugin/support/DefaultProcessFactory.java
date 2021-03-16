package com.echemi.maven.plugin.support;

import com.echemi.maven.plugin.constant.MethodEnum;
import com.echemi.maven.plugin.entity.Config;
import com.echemi.maven.plugin.entity.FileInfo;
import com.echemi.maven.plugin.entity.PageInfo;
import com.echemi.maven.plugin.utils.BaseUtils;
import com.echemi.maven.plugin.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author jacob
 * created in  2021/3/13 15:22
 * modified By:
 */
public class DefaultProcessFactory extends AbstractProcessFactory {
	public DefaultProcessFactory(Config config) {
		super(config);
	}

	@Override
	public void init(final String webapp) {
		if (files == null) {
			files = new HashMap<>(64);
		}
		
		ArrayList<String> allSupportFileTypeList = new ArrayList<>();
		allSupportFileTypeList.add(FileInfo.CSS);
		allSupportFileTypeList.add(FileInfo.JS);
		allSupportFileTypeList.addAll(Arrays.asList(FileInfo.IMAGE.split(",")));
		getFileInfoByType(files, webapp, allSupportFileTypeList);
		for (Map.Entry<String, FileInfo> file : files.entrySet()) {
			logger.debug("find type:" + file.getValue().getFileType() + " file:" + file.getKey() + " md5:" + file.getValue().getFileVersion());
		}
		if (pages == null) {
			pages = new ArrayList<>();
		}
		getProcessPage(pages, webapp, config.getSuffix());

		String out = config.getOutDirRoot();
		for (PageInfo pageInfo : pages) {
			String path = pageInfo.getFile().getPath();
			path = path.substring(webapp.length(), path.length());
			String temp;
			if (path.endsWith(FileUtils.getSystemFileSeparator())) {
				temp = out + path;
			} else {
				temp = out + FileUtils.getSystemFileSeparator() + path;
			}
			int lastIndexOf = temp.lastIndexOf(FileUtils.getSystemFileSeparator());
			String sub = temp.substring(0, lastIndexOf);
			File file = new File(sub);
			if (!file.exists()) {
				file.mkdirs();
			}
			pageInfo.setOutFile(new File(temp));

		}

	}

	@Override
	public void execute() {
		if (null == config) {
			logger.error("config is null");
		}
		List<FileInfo> processSuccessFiles = new ArrayList<>();
		for (PageInfo pageInfo : pages) {
			try {
				String strAll = FileUtils.readFileToStr(pageInfo.getFile(), config.getSourceEncoding());
				List<String> saveHtml = new ArrayList<>();
				if (strAll == null || strAll.length() == 0) {
					continue;
				}
				StringBuffer sb = new StringBuffer(strAll);
				int cIndex = processVersion(sb, 0, processSuccessFiles, FileInfo.CSS);
				int jIndex = processVersion(sb, 0, processSuccessFiles, FileInfo.JS);
				int iIndex = processVersion(sb, 0, processSuccessFiles, FileInfo.IMAGE);
				if (cIndex == 1 || jIndex == 1 || iIndex == 1) {
					saveHtml.add(sb.toString());
				} else {
					saveHtml.add(sb.toString());
				}
				logger.debug("page:" + pageInfo.getFile().getName() + " Processing is complete");
				FileUtils.writeFile(pageInfo.getOutFile(), config.getSourceEncoding(), saveHtml);
			} catch (IOException e) {
				logger.error(" the file process error :" + pageInfo.getFile().getPath(), e);
			}

		}
	}

	@Override
	public void success() {

	}

	private void getFileInfoByType(Map<String, FileInfo> collected, final String rootPath, final List<String> type) {
		if (collected == null) {
			collected = new HashMap<>(64);
		}
		String root = rootPath;
		if (!root.endsWith(FileUtils.getSystemFileSeparator())) {
			root += FileUtils.getSystemFileSeparator();
		}
		List<File> fileList = new ArrayList<>();
		FileUtils.collectFiles(fileList, new File(root), type);
		for (File file : fileList) {
			if (!file.getName().contains(".")) {
				continue;
			}
			String path = file.getPath();
			path = path.substring(root.length(), path.length());
			path = BaseUtils.replaceLinuxSystemLine(path);
			
			String[] split = file.getName().split("\\.");
			String fileSuffix = split[split.length-1];
			FileInfo fileInfo = new FileInfo();
			fileInfo.setFileType(fileSuffix);
			fileInfo.setFileVersion(getFileVersion(file, config.getMethod()));
			fileInfo.setRelativelyFilePath(path);
			fileInfo.setFileName(file.getName());
			fileInfo.setFile(file);
			try {
				fileInfo.setFileHashKey(BaseUtils.getFileHashKey(file, MethodEnum.MD5_METHOD.getMethod()));
			} catch (IOException e) {
				logger.debug(e);
				fileInfo.setFileHashKey(System.currentTimeMillis() + "");
			}
			collected.put(path, fileInfo);
			file = null;
		}

	}

	private void getProcessPage(List<PageInfo> files, String webapp, List<String> suffix) {
		if (files == null) {
			files = new ArrayList<>();
		}
		List<File> fileList = new ArrayList<>();
		FileUtils.collectFiles(fileList, new File(webapp), suffix);
		for (File file : fileList) {
			PageInfo pageInfo = new PageInfo();
			pageInfo.setFile(file);
			files.add(pageInfo);
		}
	}
}

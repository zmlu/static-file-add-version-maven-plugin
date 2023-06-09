package com.echemi.maven.plugin.support;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.echemi.maven.plugin.constant.FileTypeEnum;
import com.echemi.maven.plugin.constant.MethodEnum;
import com.echemi.maven.plugin.entity.Config;
import com.echemi.maven.plugin.entity.FileInfo;
import com.echemi.maven.plugin.entity.PageInfo;
import com.echemi.maven.plugin.utils.BaseUtils;
import com.echemi.maven.plugin.utils.FileUtils;
import com.echemi.maven.plugin.utils.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jacob created in  2021/3/13 15:22 modified By:
 */
public class DefaultProcessFactory extends AbstractProcessFactory {
	public DefaultProcessFactory(Config config) {
		super(config);
	}

	@Override
	public void init(final String webapp) {
		this.webapp = webapp;
		if (files == null) {
			files = new HashMap<>(64);
		}

		getFileInfo(files, webapp, Config.getSuffixList());
//		for (Map.Entry<String, FileInfo> file : files.entrySet()) {
//			logger.debug("find type:" + file.getValue().getFileType() + " file:" + file.getKey() + " md5:" + file.getValue().getFileVersion());
//		}
		if (pages == null) {
			pages = new ArrayList<>();
		}
		getProcessPage(pages, webapp, config.getSuffix());

		String out = config.getOutDirRoot();
		for (PageInfo pageInfo : pages) {
			String path = pageInfo.getFile().getPath();
			path = path.substring(webapp.length(), path.length());
			String temp;
			if (path.startsWith(FileUtils.getSystemFileSeparator())) {
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
				if (strAll == null || strAll.length() == 0) {
					continue;
				}
				StringBuffer sb = new StringBuffer(strAll);
				JSONObject selfConfig = Config.getSelfConfig();
				JSONObject processFileType = selfConfig.getJSONObject("process_file_type");
				for (String fileTypeStr : processFileType.keySet()) {
					JSONObject fileTypeObj = processFileType.getJSONObject(fileTypeStr);
					JSONArray settings = fileTypeObj.getJSONArray("settings");
					for (int i = 0; i < settings.size(); i++) {
						processVersion(pageInfo, sb, 0, processSuccessFiles, FileTypeEnum.valueOf(fileTypeStr), settings.getJSONObject(i));
					}
				}
				List<String> saveHtml = new ArrayList<>();
				if (StringUtils.isNotEmpty(sb)) {
					saveHtml.add(sb.toString());
				}
//				logger.debug("page:" + pageInfo.getFile().getName() + " Processing is complete");
				FileUtils.writeFile(pageInfo.getOutFile(), config.getSourceEncoding(), saveHtml);
			} catch (IOException e) {
				logger.error(" the file process error :" + pageInfo.getFile().getPath(), e);
			}
		}

		String out = config.getOutDirRoot();
		for (String fileInfoKey : files.keySet()) {
			FileInfo fileInfo = files.get(fileInfoKey);
			if (fileInfo.getFinalFileName()!=null && fileInfo.isNeedRename()) {
				try {
					// 将文件重命名
					String path = fileInfo.getFile().getPath();
					path = path.substring(webapp.length());
					String temp;
					if (path.startsWith(FileUtils.getSystemFileSeparator())) {
						temp = out + path;
					} else {
						temp = out + FileUtils.getSystemFileSeparator() + path;
					}
					File destOriginalFile = new File(temp);
					// 重命名
					File renamedFile = FileUtil.rename(destOriginalFile, fileInfo.getFinalFileName(), true);
					// 删除原来文件
					FileUtil.del(destOriginalFile);
					// 更新新文件的时间戳
					renamedFile.setLastModified(fileInfo.getLastModified());
				} catch (Exception e) {
					logger.error(" the fileInfo process error :" + fileInfo.getFile().getPath(), e);
				}
			}
		}
	}
	
	private void getFileInfo(Map<String, FileInfo> collected, final String rootPath, final List<String> suffix) {
		if (collected == null) {
			collected = new HashMap<>(64);
		}
		String root = rootPath;
		if (!root.endsWith(FileUtils.getSystemFileSeparator())) {
			root += FileUtils.getSystemFileSeparator();
		}
		List<File> fileList = new ArrayList<>();
		FileUtils.collectFiles(fileList, new File(root), suffix);
		for (File file : fileList) {
			if (!file.getName().contains(".")) {
				continue;
			}
			String path = file.getPath();
			path = path.substring(root.length(), path.length());
			path = BaseUtils.replaceLinuxSystemLine(path);
			
			String[] split = file.getName().split("\\.");
			String fileSuffix = split[split.length - 1];
			FileInfo fileInfo = new FileInfo();
			fileInfo.setFileType(fileSuffix);
			fileInfo.setFileVersion(getFileVersion(file, config.getMethod()));
			fileInfo.setRelativelyFilePath(path);
			fileInfo.setFileName(file.getName());
			fileInfo.setLastModified(file.lastModified());
			fileInfo.setFile(file);
			try {
				fileInfo.setFileHashKey(BaseUtils.getFileHashKey(file, MethodEnum.MD5_METHOD.getMethod()));
			} catch (IOException e) {
				logger.debug(e);
				fileInfo.setFileHashKey(System.currentTimeMillis() + "");
			}
			collected.put(path, fileInfo);
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

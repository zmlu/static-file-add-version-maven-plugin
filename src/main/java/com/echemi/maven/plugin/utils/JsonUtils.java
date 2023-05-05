package com.echemi.maven.plugin.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class JsonUtils {
	
	/**
	 * 读取json文件，返回json串
	 *
	 * @param fileName
	 * @return
	 */
	public static JSONObject readJsonFile(String fileName) {
		String jsonStr;
		try {
			Reader reader = new InputStreamReader(Objects.requireNonNull(JsonUtils.class.getClassLoader().getResourceAsStream(fileName)), StandardCharsets.UTF_8);
			int ch;
			StringBuilder sb = new StringBuilder();
			while ((ch = reader.read()) != -1) {
				sb.append((char) ch);
			}
			reader.close();
			jsonStr = sb.toString();
			return JSONObject.parseObject(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

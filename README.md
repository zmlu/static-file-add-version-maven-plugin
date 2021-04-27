# 简介

`static-file-add-version-maven-plugin`是一个自动为网页添加js.css的版本号工具，支持多种方法版本号添加，采集文件的md5值进行文件版本号修订

# Quick Start

### jsp

```xml
<!--静态资源添加版本号 开始-->
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-war-plugin</artifactId>
	<version>3.2.3</version>
	<configuration>
		<useCache>true</useCache>
	</configuration>
	<executions>
		<execution>
			<id>prepare-war</id>
			<phase>prepare-package</phase>
			<goals>
				<goal>exploded</goal>
			</goals>
		</execution>
	</executions>
</plugin>
<plugin>
<groupId>com.echemi.maven.plugin</groupId>
<artifactId>static-file-add-version-maven-plugin</artifactId>
<version>0.2.2</version>
<executions>
	<execution>
		<phase>prepare-package</phase>
		<goals>
			<goal>process</goal>
		</goals>
	</execution>
</executions>
<configuration>
	<cdnJsElName>webRootStaticCdn</cdnJsElName>
	<cdnCssElName>webRootStaticCdn</cdnCssElName>
	<cdnImageElName>webRootStaticCdn</cdnImageElName>
	<elNameIncludePath>static_v3</elNameIncludePath>
	<suffix>
		<param>jsp</param>
		<param>css</param>
	</suffix>
</configuration>
</plugin>
<!--静态资源添加版本号 结束-->
```

### thymleaf

```xml
<!--静态资源添加版本号 开始-->
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-war-plugin</artifactId>
	<version>3.2.3</version>
	<configuration>
		<useCache>true</useCache>
	</configuration>
	<executions>
		<execution>
			<id>prepare-war</id>
			<phase>prepare-package</phase>
			<goals>
				<goal>exploded</goal>
			</goals>
		</execution>
	</executions>
</plugin>
<plugin>
	<groupId>com.echemi.maven.plugin</groupId>
	<artifactId>static-file-add-version-maven-plugin</artifactId>
	<version>0.3.0</version>
	<executions>
		<execution>
			<phase>prepare-package</phase>
			<goals>
				<goal>process</goal>
			</goals>
		</execution>
	</executions>
	<configuration>
		<webapp>${basedir}/src/main/resources</webapp>
		<outputDir>${project.build.directory}/classes</outputDir>
		<outputDirIncludeWebRootName>false</outputDirIncludeWebRootName>
		<cdnJsElName>webRootCdnJs</cdnJsElName>
		<cdnCssElName>webRootCdnCss</cdnCssElName>
		<cdnImageElName>webRootCdnImage</cdnImageElName>
		<elNameIncludePath>static/v2</elNameIncludePath>
		<suffix>
			<param>html</param>
			<param>css</param>
		</suffix>
	</configuration>
</plugin>
<!--静态资源添加版本号 结束-->
```
### 打包

```
mvn clean install
```
注意该插件不会在idea中生效，在package后才会生效
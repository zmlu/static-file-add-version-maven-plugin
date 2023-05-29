# What is it?

`static-file-add-version-maven-plugin`
It's a tool to automatically add version numbers to static resources, for web projects developed by JSP or Springboot.

# Project Background

Because if the site adds a CDN, then after each release, the modified static resources will not be immediately synchronized to the user side, because the old static resources have a cache on the user side, using this Maven plugin, you can generate a unique file name for each static resource file after the release, so as to achieve the problem of refreshing the cache on the user side.

# Implementation principle

This plugin works in the Maven plugin packaging stage, before generating executable jar, it will scan all static resources (css, js, jpg, png, etc.) referenced by web page files in the project, then calculate the md5 value of each static resource, generate a new name based on the md5 value, and use the new name to copy the packaged resource address, and finally replace it with a new one where it is used. Finally, replace it with the new address where it is used.

# Getting Started

1. Reference the Maven code in `pom.xml`

### JSP

```xml
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
    <version>0.5.4</version>
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
        <compressOutput>false</compressOutput>
        <inName>true</inName>
        <excludes>
            <exclude>static_v3/js/pdfjs-3.4.120-dist/</exclude>
            <exclude>static_v3/wap/js/pdfjs-3.4.120-dist/</exclude>
        </excludes>
    </configuration>
</plugin>
```

### Thymleaf

```xml
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
```

2. Execute the package command and see if the output files have version numbers added to them

```shell
mvn clean install
```

# Parameter explanation

* `suffix`: Need to replace the suffix of the content
* `domains`: Domain name, JS and CSS using the domain name configuration when the connection, non-required fields
* `method`: The method used to calculate the filename, the default is MD5_METHOD
* `versionLabel`: Version number tag, default is v
* `sourceEncoding`: File encoding, default is UTF-8
* `skipSuffix`: Skip filename suffix (name before suffix)
* `excludes`: Exclude files (only root path start is supported for now, no regular support)
* `includes`: Include files (only root path start is supported for now, no regular support)
* `outputDir`: Output file directory, default is the value of the `${project.build.directory}` variable
* `webRootName`: The name of the root directory, which defaults to the value of the `${project.build.finalName}` variable
* `cdnJsElName`: Global JS file EL prefix
* `cdnCssElName`: Global CSS file EL prefix
* `cdnImageElName`: Global Image file EL prefix
* `elNameIncludePath`: Local static resource directory replaced by global EL prefix
* `outputDirIncludeWebRootName`: Whether the output directory contains the project name
* `compressOutput`: Whether to compress the output file
* `inName`: Whether to replace the file name with the version number

# License

`static-file-add-version-maven-plugin` is released under the terms of the MIT license. See [COPYING](LICENSE) for more information or see https://opensource.org/licenses/MIT.
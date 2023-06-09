package com.echemi.maven.plugin.utils;

import com.echemi.maven.plugin.constant.MethodEnum;
import com.echemi.maven.plugin.entity.Config;
import com.echemi.maven.plugin.support.DefaultProcessFactory;
import com.echemi.maven.plugin.support.ProcessFactory;
import com.echemi.maven.plugin.support.logger.SystemLogger;
import org.apache.maven.plugin.logging.Log;
import org.junit.Test;

import java.io.File;
import java.util.*;

/**
* @Package
*	 com.echemi.maven.plugin.utils
* @ClassName:
*	 JCVFactoryTest
* @since
*	  V1.0
* @author
*		jacob
* @date
*		2021/03/07-13:56:29
* @version
*		V1.0
*/
@SuppressWarnings({"unused"})
public class JCVFactoryTest {
    private static final Log logger = new SystemLogger();
    
//    @Test
//    public void test(){
//        Config config = new Config();
//        config.setVersionLabel("v");
//        config.setSourceEncoding("UTF-8");
//        config.setIncludes(Collections.<String>emptyList());
//        config.setExcludes(Collections.<String>emptyList());
//        config.setDomains(Collections.<String>emptyList());
//        config.setMethod(MethodEnum.MD5_METHOD);
//        config.setSuffix(Arrays.asList("html","css"));
//        config.setOutDirRoot("~/Downloads/test");
//        config.setCdnJsElName("webRootCdnJs");
//        config.setCdnCssElName("webRootCdnCss");
//        config.setCdnImageElName("webRootCdnImage");
//        config.setElNameIncludePath("static");
//
//        ProcessFactory processFactory=new DefaultProcessFactory(config);
//        processFactory.buildLoggerFactory(logger);
//        processFactory.init("/Volumes/SSD/codeup/echemi_group/src/main/resources");
//        processFactory.execute();
//    }
//
//    @Test
//    public void testJsp(){
//        Config config = new Config();
//        config.setVersionLabel("v");
//        config.setSourceEncoding("UTF-8");
//        config.setIncludes(Collections.<String>emptyList());
//        List<String> excludes = new ArrayList<>();
//        excludes.add("static_v3/js/pdfjs-3.4.120-dist/");
//        excludes.add("static_v3/wap/js/pdfjs-3.4.120-dist/");
//        config.setExcludes(excludes);
//        config.setDomains(Collections.<String>emptyList());
//        config.setMethod(MethodEnum.MD5_METHOD);
//        config.setSuffix(Arrays.asList("jsp","css"));
//        config.setOutDirRoot("C:\\Users\\Jacob\\Downloads\\echemi_web_en_test");
//        config.setCdnJsElName("webRootStaticCdn");
//        config.setCdnCssElName("webRootStaticCdn");
//        config.setCdnImageElName("webRootStaticCdn");
//        config.setElNameIncludePath("static_v3");
//        config.setCompressOutput(true);
//        config.setInName(true);
//        config.setVersionLength(6);
//
//        ProcessFactory processFactory=new DefaultProcessFactory(config);
//        processFactory.buildLoggerFactory(logger);
//        processFactory.init("C:\\dev\\codeup\\echemi_web_en\\src\\main\\webapp");
//        processFactory.execute();
//    }
}

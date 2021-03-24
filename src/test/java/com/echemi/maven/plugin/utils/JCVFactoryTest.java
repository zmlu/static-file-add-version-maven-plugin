package com.echemi.maven.plugin.utils;

import com.echemi.maven.plugin.constant.MethodEnum;
import com.echemi.maven.plugin.entity.Config;
import com.echemi.maven.plugin.support.DefaultProcessFactory;
import com.echemi.maven.plugin.support.ProcessFactory;
import com.echemi.maven.plugin.support.logger.SystemLogger;
import org.apache.maven.plugin.logging.Log;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

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
    
    @Test
    public void test(){
        
//        Config config = new Config();
//        config.setVersionLabel("v");
//        config.setSourceEncoding("UTF-8");
//        config.setIncludes(Collections.singletonList("src/main/webapp/webpage/activities/special/latinAmerica.jsp"));
//        config.setExcludes(Collections.<String>emptyList());
//        config.setDomains(Collections.<String>emptyList());
//        config.setMethod(MethodEnum.MD5_METHOD);
//        config.setSuffix(Arrays.asList("jsp","css"));
//        config.setOutDirRoot("/Users/jacob/Downloads/test");
//        config.setCdnJsElName("webRootStaticCdn");
//        config.setCdnCssElName("webRootStaticCdn");
//        config.setCdnImageElName("webRootStaticCdn");
//        config.setElNameIncludePath("static_v3");
//
//        ProcessFactory processFactory=new DefaultProcessFactory(config);
//        processFactory.buildLoggerFactory(logger);
//        processFactory.init("/Users/jacob/dev/svn/echemi/SOURCE/JAVA/Branch/echemi_web_en/src/main/webapp");
//        processFactory.execute();
    }
}

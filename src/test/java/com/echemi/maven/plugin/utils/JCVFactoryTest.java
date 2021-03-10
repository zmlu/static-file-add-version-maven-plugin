package com.echemi.maven.plugin.utils;

import java.io.IOException;

import org.junit.Test;

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
    
    
    
    @Test
    public void testprocessJSCSS(){
//        String html="<link rel='stylesheet' type='text/css' href='http://style.test.com/css/public.css' />";
//        String html2="<link rel=\"stylesheet2\" type=\"text/css2\" href=\"http://style.test2.com/css/public.css\" />";
       // StringBuffer sb=new StringBuffer(html+html2);
       // JCVFactory jc=new JCVFactory();
       // System.out.println(jc.processCSS(sb,0));
    }
    
    
    @Test
    public void testprocessCSS() throws IOException{
//        String html="<link rel=\"stylesheet\" href=\"${webRootStaticCdn}/css/onlineExhibition/EX00001/conference.css\">";
//        //String html2="<link rel=\"stylesheet2\" type=\"text/css2\" href=\"http://style.test2.com/css/public.css\" />";
//       // String html=FileUtils.readToString(new File("/home/user/d17-workspaces/eclipse-my/zy-storage/storage-center/src/main/webapp/index.html"), "utf-8")[0];
//        StringBuffer sb=new StringBuffer(html);
//        JCVFactory jc=new JCVFactory();
//        List<String> baseCssDomin=new ArrayList<>();
//        baseCssDomin.add("http://style.test.com");
//        
//        Map<String, JCVFileInfo> jcvs=new HashMap<>();
//        
//        JCVFileInfo ji=new JCVFileInfo();
//        ji.setFileType(JCVFileInfo.CSS);
//        ji.setFileVersion("");
//        ji.setRelativelyFilePath("static_v3/css/onlineExhibition/EX00001/conference.css");
//        ji.setFileName("conference.css");
//        ji.setFileType(JCVFileInfo.CSS);
//        jcvs.put("static_v3/css/onlineExhibition/EX00001/conference.css", ji);
//        
//        jc.setBaseCssDomin(baseCssDomin);
//        jc.setJcvs(jcvs);
//        jc.setCssEn(JCVMethodEnum.MD5_METHOD);
//        jc.setVersionLable("v");
//        jc.setGlobaCsslPrefixPath("");
//        jc.setGlobaCssElPrefix("${webRootStaticCdn}");
//        jc.setGlobaElEqualStaticPath("static_v3");
//        jc.setCompressionCss(true);
//       System.out.println(jc.processCSS(sb,0));
//        ca(sb);
//        System.out.println(sb.toString());
    }
    
    
    @Test
    public void testprocessJS() throws Exception{
//      //  String html="<link rel='stylesheet' type='text/css' href='http://style.test.com/css/public.css' />";
//        //String html2="<link rel=\"stylesheet2\" type=\"text/css2\" href=\"http://style.test2.com/css/public.css\" />";
//        String html=FileUtils.readToStr(new File("/Users/jacob/Desktop/jcv-maven-plugin-master/src/main/resources/about.jsp") , "utf-8");
//        
//        char[] charArray = html.toCharArray();
//        
//        
//        StringBuffer sb=new StringBuffer(html);
//        JCVFactory jc=new JCVFactory();
//        List<String> baseCssDomin=new ArrayList<>();
//      //  baseCssDomin.add("http://style.test.com");
//        
//        Map<String, JCVFileInfo> jcvs=new HashMap<>();
//
//        JCVFileInfo ji=new JCVFileInfo();
//        ji.setFileVersion("111");
//        ji.setRelativelyFilePath("static_v3/css/onlineExhibition/EX00001/conference.css");
//        ji.setFileName("conference.css");
//        ji.setFileType(JCVFileInfo.CSS);
//        jcvs.put("static_v3/css/onlineExhibition/EX00001/conference.css", ji);
//
//        jc.setJcvs(jcvs);
//        jc.setCssEn(JCVMethodEnum.MD5_METHOD);
//        jc.setVersionLable("v");
//        jc.setGlobaCsslPrefixPath("");
//        jc.setGlobaCssElPrefix("${webRootStaticCdn}");
//        jc.setGlobaElEqualStaticPath("static_v3");
//        jc.setCompressionCss(false);
//        System.out.println(jc.processCSS(sb,0));
//        System.out.println(sb.toString());

    }
    
    
   
    
}

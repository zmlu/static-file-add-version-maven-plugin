package com.jacob.maven.plugin.utils;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

/**  
* @Package 
*	 com.iqarr.maven.plugin.utils
* @ClassName: 
*	 HtmlUtilsTest  
* @since 
*	  V1.0
* @author 
*		zhangyong   
* @date 
*		2017/01/07-11:42:28
* @version 
*		V1.0      
*/
@SuppressWarnings({"unused"})
public class HtmlUtilsTest {
    
    /**
     * Test method for {@link com.jacob.maven.plugin.utils.HtmlUtils#htmlTolinks(java.lang.String)}.
     */
    @Test
    public void testHtmlTolinks() {
    
       
    }
    
    
    /**
     * Test method for {@link com.jacob.maven.plugin.utils.HtmlUtils#cleanBaseComments(StringBuffer, String)}.
     * @throws Exception 
     */
    @Test
    public void testCleanBaseComments() throws Exception {
       String readToStr = FileUtils.readToStr(new File(this.getClass().getClassLoader().getResource("demo.js").getPath()) , "utf-8");
        
        //String readToStr = FileUtils.readToStr(new File("/home/user/d17-workspaces/eclipse-d17/d17-wyb/d17-wyb-web/target/wyb/js/jquery/jquery.form.js") , "utf-8");
        
        StringBuffer sb=new StringBuffer(readToStr);
       // HtmlUtils.cleanBaseComments(sb, "utf-8");
       
    }
    /**
     * Test method for {@link com.jacob.maven.plugin.utils.HtmlUtils#cleanBase2Comments(StringBuffer, String)}.
     * @throws Exception 
     */
    @Test
    public void testCleanBase2Comments() throws Exception {
        String readToStr = FileUtils.readToStr(new File(this.getClass().getClassLoader().getResource("demo.js").getPath()) , "utf-8");
        StringBuffer sb=new StringBuffer(readToStr);
       // HtmlUtils.cleanBase2Comments(sb, "utf-8");
       // int indexOf = sb.indexOf("//", 458);
        //System.out.println(sb.toString());
       // assertTrue(indexOf==-1);
    }
    /**
     * Test method for {@link com.jacob.maven.plugin.utils.HtmlUtils#checkJsMarkClosed(char[], int, int, char)}.
     * @throws Exception 
     */
    @Test
    public void testCheckJsMarkClosed() throws Exception{
        String readToStr = FileUtils.readToStr(new File(this.getClass().getClassLoader().getResource("demo.js").getPath()) , "utf-8");
        StringBuffer sb=new StringBuffer(readToStr); 
        
       // boolean checkJsMarkClosed = HtmlUtils.checkJsMarkClosed(sb.toString().toCharArray(), 101, 190, '"');
       // assertTrue(checkJsMarkClosed);
    }
    
    /**
     * Test method for {@link com.jacob.maven.plugin.utils.HtmlUtils#cleanLineFeedComments(StringBuffer, String)}.
     * @throws Exception 
     */
    @Test
    public void testCleanLineFeedComments() throws Exception{
        String readToStr = FileUtils.readToStr(new File(this.getClass().getClassLoader().getResource("demo.js").getPath()) , "utf-8");
        StringBuffer sb=new StringBuffer(readToStr); 
        
        HtmlUtils.cleanLineFeedComments(sb, "utf-8");
        int indexOf = sb.indexOf("   ");
        assertTrue(indexOf==-1);
       // System.out.println(sb.toString());
    }
    
    @Test
    public void testCompression() throws Exception{
        String readToStr = FileUtils.readToStr(new File(this.getClass().getClassLoader().getResource("demo.js").getPath()) , "utf-8");
        StringBuffer sb=new StringBuffer(readToStr);
      //  HtmlUtils.cleanBaseComments(sb, "utf-8");
       // HtmlUtils.cleanBase2Comments(sb, "utf-8");
         HtmlUtils.cleanLineFeedComments(sb, "utf-8");
         int indexOf = sb.indexOf("/r");
         assertTrue(indexOf==-1);
       // System.out.println(sb.toString());
    }
    
    @Test
    public void testcleanBaseAllComments() throws Exception{
        ///home/user/d17-workspaces/eclipse-d17/d17-wyb/d17-wyb-web/target/wyb/js/validate/Vcommon.js
       String readToStr = FileUtils.readToStr(new File(this.getClass().getClassLoader().getResource("demo.js").getPath()) , "utf-8");;
        StringBuffer sb=new StringBuffer(readToStr);
         HtmlUtils.cleanBaseAllComments(sb, "utf-8",false);
        HtmlUtils.cleanLineFeedComments(sb, "utf-8");
       // int indexOf = sb.indexOf("/*");
       // assertTrue(indexOf==-1);
    }
   
    
}

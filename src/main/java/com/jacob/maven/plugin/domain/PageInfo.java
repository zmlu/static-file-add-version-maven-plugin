package com.jacob.maven.plugin.domain;

import java.io.File;

/**  
* @Package 
*	 com.iqarr.maven.plugin.domain
* @ClassName: 
*	 PageInfo  
* @since 
*	  V1.0
* @author 
*		zhangyong   
* @date 
*		2017/01/07-10:01:07
* @version 
*		V1.0      
*/
public class PageInfo {
    
    private File file;
    
    
    /**
     * 输出文件位置
     */
    private File  OutFile;

    /**
     * 获取  
     * @return file
     */
    public File getFile() {
        return file;
    }

    /**
     * 设置 
     * @param file 
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * 获取 输出文件位置 
     * @return outFile
     */
    public File getOutFile() {
        return OutFile;
    }

    /**
     * 设置 输出文件位置
     * @param outFile 输出文件位置
     */
    public void setOutFile(File outFile) {
        OutFile = outFile;
    }


    @Override
    public String toString() {
        return "PageInfo{" +
                "file=" + file +
                ", OutFile=" + OutFile +
                '}';
    }
}

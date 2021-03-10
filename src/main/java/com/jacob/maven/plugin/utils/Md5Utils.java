package com.jacob.maven.plugin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**  
* @Package 
*	 com.iqarr.maven.plugin.utils
* @ClassName: 
*	 Md5Utils  
* @since 
*	  V1.0
* @author 
*		zhangyong   
* @date 
*		2017/01/06-16:18:22
* @version 
*		V1.0      
*/
public class Md5Utils {

    /**
     * 
     * 计算文件md5
     * @param file
     * @return
     */
    public static String getFileMD5(File file) throws Exception {  
        if (!file.exists() || !file.isFile()) {  
            return null;  
        }  
        MessageDigest digest = null;  
        FileInputStream in = null;  
        byte buffer[] = new byte[1024];  
        int len;  
        try {  
            digest = MessageDigest.getInstance("MD5");  
            in = new FileInputStream(file);  
            while ((len = in.read(buffer, 0, 1024)) != -1) {  
                digest.update(buffer, 0, len);  
            }  
            in.close();  
        } catch (IOException e) {  
            throw e;
        }  
        BigInteger bigInt = new BigInteger(1, digest.digest());  
        return bigInt.toString(16);  
    }  
  
}

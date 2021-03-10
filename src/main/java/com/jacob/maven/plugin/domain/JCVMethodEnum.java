package com.jacob.maven.plugin.domain;

/**
 * @Package
 *          com.iqarr.maven.plugin
 * @ClassName:
 *             JCVMethodEnum
 * @since
 *        V1.0
 * @author
 *         zhangyong
 * @date
 *       2017/01/06-14:13:36
 * @version
 *          V1.0
 */
public enum JCVMethodEnum {
    
    /** 时间戳 **/
    TIMESTAMP_METHOD("timestamp",1),
    
    /** md5版本号 **/
    MD5_METHOD("md5",2),
    
    /** md5文件名 **/
    MD5FileName_METHOD("md5FileName",2),
    
    ;
    
    private String method;
    
    private int    id;
    
    
    /* 
     * @param method
     * @param id
     */
    private JCVMethodEnum(String method, int id) {
        this.method = method;
        this.id = id;
    }
    
    /**
     * 获取
     * 
     * @return method
     */
    public String getMethod() {
        return method;
    }
    
    /**
     * 设置
     * 
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }
    
    /**
     * 获取
     * 
     * @return id
     */
    public int getId() {
        return id;
    }
    
    /**
     * 设置
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    
}

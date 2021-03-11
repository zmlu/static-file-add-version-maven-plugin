package com.echemi.maven.plugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.echemi.maven.plugin.domain.JCVFileInfo;
import com.echemi.maven.plugin.domain.JCVMethodEnum;
import com.echemi.maven.plugin.domain.PageInfo;
import com.echemi.maven.plugin.domain.YUIConfig;
import com.echemi.maven.plugin.utils.FileUtils;
import com.echemi.maven.plugin.utils.Md5Utils;
import org.codehaus.plexus.util.StringUtils;

/**  
* @Package 
*	 com.echemi.maven.plugin
* @ClassName: 
*	 JCMojo  
* @since 
*	  V1.0
* @author 
*		jacob   
* @date 
*		2021/03/06-10:34:34
* @version 
*		V1.0      
*/
@Mojo( name = "process" , defaultPhase = LifecyclePhase.PROCESS_RESOURCES, threadSafe = true)
public class JCVMojo extends AbstractMojo {
    
    
    /** 启动时间**/
    private long timeStart=0;
    
    /**
     * 输出文件目录
     */
    @Parameter( defaultValue = "${project.build.directory}", property = "outputDir", required = true )
    private File outputDirectory;
    
    /**
     * webapp目录
     */
    @Parameter( defaultValue = "${basedir}/src/main/webapp", property = "webappDirectory", required = true )
    private File webappDirectory;
    
    /**
     * 默认检查文件后缀
     */
    @Parameter(defaultValue ="jsp")
    private List<String> suffixs;
    
    /**
     * 基本的域名　script标签的src的前缀 
     * 该配置适合采用动静分离等方法，相对路径不需要配置
     */
    @Parameter
    private List<String> baseJsDomin = new ArrayList<>();
    
    /**
     * 基本的域名　css 标签的link的前缀
     * 该配置适合采用动静分离等方法，相对路径不需要配置
     */
    @Parameter
    private List<String> baseCssDomin = new ArrayList<>();
    
    /**
     * 全局js文件前缀  最中计算路径 baseCssDomin+globaJslPrefixPath+实际地址
     * 不配置该属性，就从根目录全部扫描
     */
    @Parameter(defaultValue ="")
    private String globaJslPrefixPath="";
    
    /**
     * 全局css文件前缀
     * 不配置该属性，就从根目录全部扫描
     */
    @Parameter(defaultValue ="")
    private String globaCsslPrefixPath="";

    /**
     * 全局Image文件前缀
     * 不配置该属性，就从根目录全部扫描
     */
    @Parameter(defaultValue ="")
    private String globaImagelPrefixPath="";

    /**
     * 全局JS文件EL前缀
     */
    @Parameter(defaultValue ="")
    private String cdnJsElName ="";

    /**
     * 全局CSS文件EL前缀
     */
    @Parameter(defaultValue ="")
    private String cdnCssElName ="";
    
    /**
     * 全局Image文件EL前缀
     */
    @Parameter(defaultValue ="")
    private String cdnImageElName ="";

    /**
     * 全局EL前缀替换成的本地静态资源目录
     */
    @Parameter(defaultValue ="")
    private String elNameIncludePath ="";
    
    /**
     * js 使用方法
     */
    @Parameter(defaultValue ="MD5_METHOD")
    private JCVMethodEnum globaJsMethod;
    
    /**
     * css 使用方法
     */
    @Parameter(defaultValue ="MD5_METHOD")
    private JCVMethodEnum globaCssMethod;

    /**
     * image 使用方法
     */
    @Parameter(defaultValue ="MD5_METHOD")
    private JCVMethodEnum globaImageMethod;
    
    /**根目录名称 **/
    @Parameter(defaultValue ="${project.build.finalName}")
    private String webRootName;
    
    /**版本号标签 **/
    @Parameter(defaultValue ="v")
    private String versionLable;
    /**文件编码 **/
    @Parameter(defaultValue ="UTF-8")
    private String sourceEncoding;
    /**清除页面注释 **/
    @Parameter(defaultValue ="false")
    private boolean clearPageComment;
    
    /** 使用md5文件名输出js css 指定目录**/
    @Parameter(defaultValue ="")
    private String outJsCssImageDirPath;
    
    //version 0.0.2
    
    /**压缩css **/
    @Parameter(defaultValue ="false")
    private boolean compressionCss;
    
    /** 压缩js**/
    @Parameter(defaultValue ="false")
    private boolean compressionJs;
    
    /**压缩文件后缀 **/
    @Parameter(defaultValue ="min")
    private String userCompressionSuffix = "min";
    
    /** 排除js文件(只支持全路径匹配)**/
    @Parameter
    private List<String> excludesJs;
    
    /** 排除css文件(只支持全路径匹配)**/
    @Parameter
    private List<String> excludesCss;
    
    /** 排除image文件(只支持全路径匹配)**/
    @Parameter
    private List<String> excludesImage;
    
    /** yui config**/
    @Parameter
    private YUIConfig yuiConfig;
    
    /**跳过文件名后缀(后缀之前的名称) **/
    @Parameter(defaultValue =".min")
    private String braekFileNameSuffix;
    
    /*
    * <p>Title: execute</p>  
    * <p>Description: </p>  
    * @throws MojoExecutionException
    * @throws MojoFailureException  
    * @see org.apache.maven.plugin.Mojo#execute()  
    */
    
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("===================static-file-add-version-maven-plugin==========================");
//        showAsc();
        getLog().debug("find suffixs size:"+suffixs.size());
        getLog().debug("build webRootName:"+webRootName);
        getLog().debug("build sourceEncoding:"+sourceEncoding);
        
      //显示日志
        getLog().debug("web app Dir:"+webappDirectory.getPath());
        getLog().debug("out Dir:"+outputDirectory.getPath());
        getLog().debug("system is linux:"+FileUtils.getSystemFileSeparatorIslinux());
        getLog().debug("css method is :"+globaCssMethod.getMethod());
        getLog().debug("js method is :"+globaJsMethod.getMethod());
        
       
        String webRoot=webappDirectory.getPath();
        
        timeStart=new Date().getTime();
       
        
        Map<String,JCVFileInfo> collected= new HashMap<String,JCVFileInfo>();
        getAllCssFile(collected,webRoot);
        getAllJsFile(collected,webRoot);
        getAllImageFile(collected,webRoot);

        JCVFactory jcf=new JCVFactory(
                        collected, globaJsMethod,globaCssMethod,globaImageMethod, versionLable, baseJsDomin,
                        baseCssDomin, globaJslPrefixPath, globaCsslPrefixPath,globaImagelPrefixPath, cdnCssElName, cdnJsElName,cdnImageElName, elNameIncludePath, sourceEncoding, clearPageComment,  getLog(), outJsCssImageDirPath,
                        compressionCss, compressionJs,userCompressionSuffix,
                        excludesJs,excludesCss,excludesImage,
                        yuiConfig,braekFileNameSuffix);
        List<PageInfo> pages=new ArrayList<PageInfo>();
        getAllProcessFile(pages,webRoot,suffixs);
        for(int i=0;i<pages.size();i++){
            //webRootName
           String out= outputDirectory.getPath()+FileUtils.getSystemFileSeparator()+webRootName;

           String path = pages.get(i).getFile().getPath();
           path= path.replaceAll(webRoot, "");
           String tm="";
           if(path.endsWith(FileUtils.getSystemFileSeparator())){
              tm=out+path;
           }else {
               tm=out+FileUtils.getSystemFileSeparator()+path;
           }
          int lastIndexOf = tm.lastIndexOf(FileUtils.getSystemFileSeparator());
          String sub = tm.substring(0, lastIndexOf);
          File f=new File(sub);
          if(!f.exists()){
              f.mkdirs();
          }
            pages.get(i).setOutFile(new File(tm));
        }
        jcf.processPageFile(pages);


        //复制MD5FileName_METHOD　文件
        if (globaCssMethod == JCVMethodEnum.MD5FileName_METHOD || globaJsMethod == JCVMethodEnum.MD5FileName_METHOD || globaImageMethod == JCVMethodEnum.MD5FileName_METHOD) {
            List<JCVFileInfo> processFiles = jcf.getProcessFiles();
            for (JCVFileInfo info : processFiles) {
                if(globaCssMethod == JCVMethodEnum.MD5FileName_METHOD && JCVFileInfo.CSS.equals(info.getFileType())){
                    copyMd5FileNameJsCssImage(info);
                }
                if(globaJsMethod == JCVMethodEnum.MD5FileName_METHOD && JCVFileInfo.JS.equals(info.getFileType())){
                    copyMd5FileNameJsCssImage(info);
                }
                if(globaImageMethod == JCVMethodEnum.MD5FileName_METHOD && JCVFileInfo.IMAGE.contains(info.getFileType())){
                    copyMd5FileNameJsCssImage(info);
                }
            }
        }
        
      //压缩，处理js css
        if (compressionJs == true || compressionCss == true) {
            String tempPath = outJsCssImageDirPath;
            if (null != outJsCssImageDirPath && !"".equals(outJsCssImageDirPath)) {
                
            } else {
                tempPath = outputDirectory.getPath() + FileUtils.getSystemFileSeparator() + webRootName;
            }
          
            jcf.processCompressionJsCss(tempPath);
        }
        
       
        
        
        //复制未处理文件 　
        if (compressionJs == true || compressionCss == true ||
                        globaCssMethod == JCVMethodEnum.MD5FileName_METHOD || globaJsMethod==JCVMethodEnum.MD5FileName_METHOD ) {
            
            List<JCVFileInfo> processFiles = jcf.getProcessFiles();
            List<JCVFileInfo> copyFiles = new ArrayList<JCVFileInfo>();
            Map<JCVFileInfo, String> processFilesMap = new HashMap<JCVFileInfo, String>();
            for (JCVFileInfo info : processFiles) {
                processFilesMap.put(info, "1");
            }
            
            for (Entry<String, JCVFileInfo> map : collected.entrySet()) {
                String string = processFilesMap.get(map.getValue());
                if (string == null &&  map.getValue().isCopy()==false) {
                    if(map.getValue().getFileType().equals(JCVFileInfo.CSS) && 
                                    (compressionCss==true || globaCssMethod == JCVMethodEnum.MD5FileName_METHOD )  ){
                        copyFiles.add(map.getValue());
                    }else  if(map.getValue().getFileType().equals(JCVFileInfo.JS) && (compressionJs==true || globaJsMethod==JCVMethodEnum.MD5FileName_METHOD)){
                        copyFiles.add(map.getValue());
                    }
                    
                    
                }
                
            }
            for (JCVFileInfo info : copyFiles) {
                copyFileJsCss(info);
            }
        }
        
        getLog().info("===============  Total time ["+(new Date().getTime()-timeStart)+" millisecond]===========================");
        getLog().info("=============================================");
    }
    
    public void copyMd5FileNameJsCssImage(JCVFileInfo jcf){
        
        String tempPath="";
        if (StringUtils.isEmpty(outJsCssImageDirPath)) {
            outJsCssImageDirPath =outputDirectory.getPath()+FileUtils.getSystemFileSeparator()+webRootName;
        }


        if(tempPath.endsWith(FileUtils.getSystemFileSeparator())){
            tempPath+= outJsCssImageDirPath +jcf.getRelativelyFilePath();
        }else {
            tempPath+= outJsCssImageDirPath +FileUtils.getSystemFileSeparator()+jcf.getRelativelyFilePath();
        }
        int lastIndexOf = tempPath.lastIndexOf(FileUtils.getSystemFileSeparator());
        tempPath = tempPath.substring(0, lastIndexOf);
        
        File f=new File(tempPath);
        if(!f.exists()){
            f.mkdirs();
        }
        try {
            if(null==jcf.getFinalFileName() ||  "".equals(jcf.getFinalFileName())){
                return;
            }
            tempPath+=FileUtils.getSystemFileSeparator()+jcf.getFinalFileName();//jcf.getFileVersion()+"."+jcf.getFileType();
            getLog().info("copy file:"+tempPath);
            FileUtils.fileChannelCopy(jcf.getFile(), new File(tempPath));
        } catch (IOException e) {
            getLog().error("copy file error:",e);
        }
        
        
    }
    
    /**
     * 
     * 复制未处理文件
     * @param jcf
     */
    public void copyFileJsCss(JCVFileInfo jcf){
        
        String tempPath="";
        if(null!= outJsCssImageDirPath &&!"".equals(outJsCssImageDirPath) ){
           
            
        }else {
            outJsCssImageDirPath =outputDirectory.getPath()+FileUtils.getSystemFileSeparator()+webRootName;
        }
        
        
        if(tempPath.endsWith(FileUtils.getSystemFileSeparator())){
            tempPath+= outJsCssImageDirPath +jcf.getRelativelyFilePath();
        }else {
            tempPath+= outJsCssImageDirPath +FileUtils.getSystemFileSeparator()+jcf.getRelativelyFilePath();
        }
        int lastIndexOf = tempPath.lastIndexOf(FileUtils.getSystemFileSeparator());
        tempPath = tempPath.substring(0, lastIndexOf);
        
        File f=new File(tempPath);
        if(!f.exists()){
            f.mkdirs();
        }
        try {
            tempPath+=FileUtils.getSystemFileSeparator()+jcf.getFileName();
            getLog().info("copy not processed file:"+tempPath);
            FileUtils.fileChannelCopy(jcf.getFile(), new File(tempPath));
        } catch (IOException e) {
            getLog().error("copy file error:",e);
        }
        
        
    }
    
    
    /**
     * 
     * 获取全部js文件
     * @param collected
     */
    public void getAllJsFile(Map<String,JCVFileInfo> collected,final String rootPath){
        if(collected==null){
            collected=new HashMap<String,JCVFileInfo>();
        }
        String webRoot=rootPath;
        List<String > su=new ArrayList<String>();
        su.add("js");
        if (globaJslPrefixPath != null && !"".equals(globaJslPrefixPath)) {
            if (webRoot.endsWith(FileUtils.getSystemFileSeparator())) {
                webRoot+=globaJslPrefixPath;
            }else {
                webRoot+=FileUtils.getSystemFileSeparator()+globaJslPrefixPath;
            }
        }
        if (!webRoot.endsWith(FileUtils.getSystemFileSeparator())) {
            webRoot+=FileUtils.getSystemFileSeparator();
        }
        List<File> listFile=new ArrayList<File>();
        FileUtils.collectFiles(listFile, new File(webRoot), su);
        JCVFileInfo jcv=null;
        for(File f:listFile){
            String path = f.getPath();
            path= path.replaceFirst(webRoot, "");
            if(jcv==null){
                jcv=new JCVFileInfo();
            }
            if(!FileUtils.getSystemFileSeparatorIslinux()){
                path= path.replaceAll("\\", "/");
            }
            jcv.setFileType(JCVFileInfo.JS);
            jcv.setFileVersion(getFileVersion(f,globaJsMethod));
            jcv.setRelativelyFilePath(path);
            jcv.setFileName(f.getName());
            jcv.setFile(f);
            collected.put(path, jcv);
            jcv=null;
        }
    }
    
    /**
     * 
     * 获取全部css文件
     * @param collected
     */
    public void getAllCssFile(Map<String,JCVFileInfo> collected,final String rootPath){
        if(collected==null){
            collected=new HashMap<String,JCVFileInfo>();
        }
        String webRoot=rootPath;
        List<String > su=new ArrayList<String>();
        su.add("css");
        if (globaCsslPrefixPath != null && !"".equals(globaCsslPrefixPath)) {
            if (webRoot.endsWith(FileUtils.getSystemFileSeparator())) {
                webRoot+=globaCsslPrefixPath;
            }else {
                webRoot+=FileUtils.getSystemFileSeparator()+globaCsslPrefixPath;
            }
        }
        if (!webRoot.endsWith(FileUtils.getSystemFileSeparator())) {
            webRoot+=FileUtils.getSystemFileSeparator();
        }
        List<File> listFile=new ArrayList<File>();
        FileUtils.collectFiles(listFile, new File(webRoot), su);
        JCVFileInfo jcv=null;
        for(File f:listFile){
            String path = f.getPath();
            path= path.replaceFirst(webRoot, "");
            if(jcv==null){
                jcv=new JCVFileInfo();
            }
            if(!FileUtils.getSystemFileSeparatorIslinux()){
                path= path.replaceAll("\\", "/");
            }
            jcv.setFileType(JCVFileInfo.CSS);
            jcv.setFileVersion(getFileVersion(f,globaCssMethod));
            jcv.setRelativelyFilePath(path);
            jcv.setFileName(f.getName());
            jcv.setFile(f);
            collected.put(path, jcv);
            jcv=null;
        }
    }


    /**
     *
     * 获取全部Image文件
     * @param collected
     */
    public void getAllImageFile(Map<String,JCVFileInfo> collected,final String rootPath){
        if(collected==null){
            collected=new HashMap<String,JCVFileInfo>();
        }
        String webRoot=rootPath;
        List<String > su=new ArrayList<String>();
        su = Arrays.asList(JCVFileInfo.IMAGE.split(","));
        if (globaImagelPrefixPath != null && !"".equals(globaImagelPrefixPath)) {
            if (webRoot.endsWith(FileUtils.getSystemFileSeparator())) {
                webRoot+=globaImagelPrefixPath;
            }else {
                webRoot+=FileUtils.getSystemFileSeparator()+globaImagelPrefixPath;
            }
        }
        if (!webRoot.endsWith(FileUtils.getSystemFileSeparator())) {
            webRoot+=FileUtils.getSystemFileSeparator();
        }
        List<File> listFile=new ArrayList<File>();
        FileUtils.collectFiles(listFile, new File(webRoot), su);
        for(File f:listFile){
            String path = f.getPath();
            path= path.replaceFirst(webRoot, "");
            
            JCVFileInfo jcv=new JCVFileInfo();
            if(!FileUtils.getSystemFileSeparatorIslinux()){
                path= path.replaceAll("\\", "/");
            }
            if (f.getName().contains(".")){
                String[] split = f.getName().split("\\.");
                jcv.setFileType(split[split.length-1]);
            }else {
                continue;
            }
            jcv.setFileVersion(getFileVersion(f,globaImageMethod));
            jcv.setRelativelyFilePath(path);
            jcv.setFileName(f.getName());
            jcv.setFile(f);
            collected.put(path, jcv);
        }
    }
    
    /**
     * 
     * 获取全部的文件
     * @param files
     * @param suffix
     */
    public void getAllProcessFile(List<PageInfo> files, String webRoot,List<String > suffix){
        if(files==null){
            files=new ArrayList<PageInfo>();
        }
        List<File> fs=new ArrayList<File>();
        FileUtils.collectFiles(fs, new File(webRoot), suffix);
        PageInfo pi=null;
        for (File file : fs) {
            if(pi==null){
                pi=new PageInfo();
            }
            pi.setFile(file);
            files.add(pi);
            pi=null;
        }
    }
    
    /**
     * 
     * 获取文件版本信息
     * @param f
     * @param en
     * @return
     */
    public  String getFileVersion(File f,JCVMethodEnum en){
        try {
            switch (en) {
                case MD5_METHOD:
                   return  Md5Utils.getFileMD5(f);
                    
               case MD5FileName_METHOD:
                   return  Md5Utils.getFileMD5(f);
                    
                case TIMESTAMP_METHOD:
                 return timeStart+"";
                
               default:
                   return  Md5Utils.getFileMD5(f);
                   
            }
        } catch (Exception e) {
           getLog().info(e.getMessage());
        }
        return timeStart+"";
       
    }
    
    public void showAsc(){
        getLog().info("      _  _______      __ ");
        getLog().info("     | |/ ____\\ \\    / / ");
        getLog().info("     | | |     \\ \\  / /  ");
        getLog().info(" _   | | |      \\ \\/ /   ");
        getLog().info("| |__| | |____   \\  /    ");
        getLog().info(" \\____/ \\_____|   \\/    ");
        getLog().info("                         ");
        getLog().info("                         ");
    }
}

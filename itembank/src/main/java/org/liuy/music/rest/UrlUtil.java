package org.liuy.music.rest;

import java.io.File;
import java.net.URL;


/**
 * 获取当前路径类
 * 
 * */
public class UrlUtil {
	
	/**
     * 取得当前类所在的文件
     * @param clazz
     * @return
     */
    public static File getClassFile(Class clazz){
        URL path = clazz.getResource(clazz.getName().substring(
                clazz.getName().lastIndexOf(".")+1)+".classs");
        if(path == null){
            String name = clazz.getName().replaceAll("[.]", "/");
            path = clazz.getResource("/"+name+".class");
        }
        return new File(path.getFile());
    }
    
    /**
     * 得到当前类的路径
     * @param clazz
     * @return
     */
    public static String getClassFilePath(Class clazz){
        try{
            return java.net.URLDecoder.decode(getClassFile(clazz).getAbsolutePath(),"UTF-8");
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "";
        }
    }

}

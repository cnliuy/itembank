package org.liuy.music.tools;

import java.io.File;

public class HtmlToPdf {  
    //wkhtmltopdf在系统中的路径  
    private static final String toPdfTool = Messages.getString("HtmlToPdf.binpath");   //$NON-NLS-1$
      
    /** 
     * html转pdf 
     * @param srcUrl html路径，可以是硬盘上的路径，也可以是网络路径 
     * @param destPath pdf保存路径 
     * @return 转换成功返回true 
     */  
    public static boolean convert(String srcUrl, String destPath){  
        File file = new File(destPath);  
        File parent = file.getParentFile();  
        //如果pdf保存路径不存在，则创建路径  
        if(!parent.exists()){  
            parent.mkdirs();  
        }  
        System.out.println("toPdfTool="+toPdfTool);
        StringBuilder cmd = new StringBuilder();  
        cmd.append(toPdfTool);  
        cmd.append(" ");   //$NON-NLS-1$
        cmd.append(srcUrl);  
        cmd.append(" ");   //$NON-NLS-1$
        cmd.append(destPath);  
          
        boolean result = true;  
        try{  
            Process proc = Runtime.getRuntime().exec(cmd.toString());  
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());  
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());  
            error.start();  
            output.start();  
            proc.waitFor();  
        }catch(Exception e){  
            result = false;  
            e.printStackTrace();  
        }  
          
        return result;  
    }  
}  
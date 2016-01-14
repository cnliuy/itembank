package org.liuy.music.tools;

import java.io.UnsupportedEncodingException;

public class CheckString {
	
	/**
	 * 判断字符串是否有乱码
	 * false---没有   ;
	 * true--- 有 
	 * */
	public static boolean cclm(String source) {
		boolean flag = false;
		//String source;
		//try {
			//source = new String(source1.getBytes(),"UTF-8");
//			System.out.println("source:"+source);
//			char ws[] = new char[] { '"', '?', ' ', '\'', '&' };
//			for (int i = 0; i < source.length(); i++) {
//				char c = source.charAt(i);
//				for (int j = 0; j < ws.length; j++) {
//					char v = ws[j];
//					if (c == v) {
//						flag = false;
//					}
//				}
//				if ((int) c == 0xfffd) {
//					flag = false;
//				}
//			}
			
		//} catch (UnsupportedEncodingException e) {			
		//	e.printStackTrace();
		//	flag= false;
		//} 
		
		
		
		if(source.indexOf("?") != -1){ 
			flag = true ;
		    //System.out.println("包含该字符串");  
		} 
		return flag;
	}

}

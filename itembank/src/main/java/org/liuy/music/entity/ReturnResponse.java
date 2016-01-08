package org.liuy.music.entity;

/**
 * retCode 返回值 ：-1 鉴权失败 不具备权限
 * 				 -2  程序异常 产生错误
 * 				  1  鉴权成功 具备权限
 * 				 
 * 
 * retInfo 返回信息
 * */
public class ReturnResponse {
	
	private Integer retCode ;
	
	private String retInfo ;

	public Integer getRetCode() {
		return retCode;
	}

	public void setRetCode(Integer retCode) {
		this.retCode = retCode;
	}

	public String getRetInfo() {
		return retInfo;
	}

	public void setRetInfo(String retInfo) {
		this.retInfo = retInfo;
	}
	
	

}

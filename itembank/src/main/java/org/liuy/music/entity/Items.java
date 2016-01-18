package org.liuy.music.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * ibiterms的 集合情况  会包含几条itembank的集合
 * 
 * iterms (itembank的集合)
 *  
 *   
 * */
@Entity
@Table(name="ibitems")
public class Items extends IdEntity {

	private String title;
	private String contents; 
	private Long  userId;
	 
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(length=90000)  //设定字段的长度 
	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

package org.liuy.music.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * ibitembank的 等级情况
 *  题目等级：
 *  简易、中等、困难
 * 
 *
 *   
 *   
 *   SQL
 *   
 *   
 *  INSERT INTO ibitembanklevel ( id, title) VALUES (1, '简易');
	INSERT INTO ibitembanklevel ( id, title) VALUES (2, '中等');
	INSERT INTO ibitembanklevel ( id, title) VALUES (3, '困难');
 *   	
 *   
 * */
@Entity
@Table(name="ibitembanklevel")
public class Itembanklevel extends IdEntity {

	private String title;
	//private String description;
	 
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

 
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

package org.liuy.music.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * ibitembank的 分类情况
 * 
 *  Itembankkind
 * 
 * 题目类型：
 *   单选题、多选题、不定项选择题、填空题、判断题、改错题、写作题、分析题
 *   
 *   SQL
 *   
 *   
 *  INSERT INTO ibitembankkind ( id, title) VALUES (1, '单选题');
	INSERT INTO ibitembankkind ( id, title) VALUES (2, '多选题');
	INSERT INTO ibitembankkind ( id, title) VALUES (3, '不定项选择题');
	INSERT INTO ibitembankkind ( id, title) VALUES (4, '填空题');
	INSERT INTO ibitembankkind ( id, title) VALUES (5, '判断题');
	INSERT INTO ibitembankkind ( id, title) VALUES (6, '改错题');
	INSERT INTO ibitembankkind ( id, title) VALUES (7, '写作题');
	INSERT INTO ibitembankkind ( id, title) VALUES (8, '分析题');
 *   	
 *   
 * */
@Entity
@Table(name="ibitembankkind")
public class Itembankkind extends IdEntity {

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

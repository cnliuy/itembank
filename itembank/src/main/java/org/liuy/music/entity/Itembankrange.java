package org.liuy.music.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

/**
 * ibitembank的知识点
 * 
 * 题目知识点：
 *   音的性质、音的高低、音的长短、音的强弱、音乐术语、译谱、省略记号、演奏法记号、
 *   装饰音、音程、和弦、大小调、大小调式中的音级音程和弦、以五声音阶为基础的民族调式、
 *   特种七声自然调式、转调、变音体系、移调、综合
 
INSERT INTO ibitembankrange( id, title ) VALUES (1,'音的性质');
INSERT INTO ibitembankrange( id, title ) VALUES (2,'音的高低');
INSERT INTO ibitembankrange( id, title ) VALUES (3,'音的长短');
INSERT INTO ibitembankrange( id, title ) VALUES (4,'音的强弱');
INSERT INTO ibitembankrange( id, title ) VALUES (5,'音乐术语');
INSERT INTO ibitembankrange( id, title ) VALUES (6,'译谱');
INSERT INTO ibitembankrange( id, title ) VALUES (7,'省略记号');
INSERT INTO ibitembankrange( id, title ) VALUES (8,'演奏法记号');
INSERT INTO ibitembankrange( id, title ) VALUES (9,'装饰音');
INSERT INTO ibitembankrange( id, title ) VALUES (10,'音程');
INSERT INTO ibitembankrange( id, title ) VALUES (11,'和弦');
INSERT INTO ibitembankrange( id, title ) VALUES (12,'大小调');
INSERT INTO ibitembankrange( id, title ) VALUES (13,'大小调式中的音级音程和弦');
INSERT INTO ibitembankrange( id, title ) VALUES (14,'以五声音阶为基础的民族调式');
INSERT INTO ibitembankrange( id, title ) VALUES (15,'特种七声自然调式');
INSERT INTO ibitembankrange( id, title ) VALUES (16,'转调');
INSERT INTO ibitembankrange( id, title ) VALUES (17,'变音体系');
INSERT INTO ibitembankrange( id, title ) VALUES (18,'移调');
INSERT INTO ibitembankrange( id, title ) VALUES (19,'综合');
 *   
 *  
 * */
@Entity
@Table(name = "ibitembankrange")
public class Itembankrange extends IdEntity {

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

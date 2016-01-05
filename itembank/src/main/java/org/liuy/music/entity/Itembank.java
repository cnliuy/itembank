package org.liuy.music.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

//JPA标识
@Entity
@Table(name = "ibitembank")
public class Itembank extends IdEntity {

	private String title;
	private String content;
	private String description;
	private String itemclassify;
	private Integer itemorder;
	private Integer weighting ;
	private Integer itemscope1 ;
	private Integer itemscope2 ;
	private Integer itemscope3 ;
	
	private Long  userId;
 
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}




	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}




	public String getItemclassify() {
		return itemclassify;
	}

	public void setItemclassify(String itemclassify) {
		this.itemclassify = itemclassify;
	}




	public Integer getItemorder() {
		return itemorder;
	}
	public void setItemorder(Integer itemorder) {
		this.itemorder = itemorder;
	}




	public Integer getWeighting() {
		return weighting;
	}
	public void setWeighting(Integer weighting) {
		this.weighting = weighting;
	}




	public Integer getItemscope1() {
		return itemscope1;
	}
	public void setItemscope1(Integer itemscope1) {
		this.itemscope1 = itemscope1;
	}




	public Integer getItemscope2() {
		return itemscope2;
	}
	public void setItemscope2(Integer itemscope2) {
		this.itemscope2 = itemscope2;
	}




	public Integer getItemscope3() {
		return itemscope3;
	}
	public void setItemscope3(Integer itemscope3) {
		this.itemscope3 = itemscope3;
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

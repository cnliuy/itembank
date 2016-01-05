/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
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
	private Integer order;
	private Integer weighting ;
	private Integer scope1 ;
	private Integer scope2 ;
	private Integer scope3 ;
	

 
	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getWeighting() {
		return weighting;
	}

	public void setWeighting(Integer weighting) {
		this.weighting = weighting;
	}

	public Integer getScope1() {
		return scope1;
	}

	public void setScope1(Integer scope1) {
		this.scope1 = scope1;
	}

	public Integer getScope2() {
		return scope2;
	}

	public void setScope2(Integer scope2) {
		this.scope2 = scope2;
	}

	public Integer getScope3() {
		return scope3;
	}

	public void setScope3(Integer scope3) {
		this.scope3 = scope3;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

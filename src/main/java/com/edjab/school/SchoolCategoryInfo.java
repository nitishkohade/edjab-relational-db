package com.edjab.school;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Model class for SchoolCategoryInfo
 */
@Entity
@Table( name = "schoolcategoryinfo" )
public class SchoolCategoryInfo implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer schoolCategoryId;
	private String schoolId;
	private String categoryName;

	public SchoolCategoryInfo() {
	}

	public SchoolCategoryInfo(String school, String catName) {
		this.schoolId = school;
		this.categoryName = catName;
	}
	
	public Integer getSchoolCategoryId() {
		return this.schoolCategoryId;
	}

	public void setSchoolCategoryId(Integer schoolCategory) {
		this.schoolCategoryId = schoolCategory;
	}

	public String getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(String school) {
		this.schoolId = school;
	}
	
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String catName) {
		this.categoryName = catName;
	}
}
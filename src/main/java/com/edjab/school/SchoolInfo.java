package com.edjab.school;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Model class for SchoolInfo
 */
@Entity
@Table( name = "schoolinfo" )
public class SchoolInfo implements java.io.Serializable, Comparable<SchoolInfo> {

	private static final long serialVersionUID = 1L;

	private Integer schoolInfoId;
	private String schoolId;
	private double latitude;
	private double longitude;
	private String city;
	private String state;
	private Integer zip;
	private double averageRating;
	private int numberOfReviews;

	public SchoolInfo() {
	}

	public SchoolInfo(String school, String cityString, String stateString, double lat, double lon,
			Integer zipString, Integer numReviews, double avgRating) {
		this.schoolId = school;
		this.latitude = lat;
		this.longitude = lon;
		this.city = cityString;
		this.state = stateString;
		this.zip = zipString;
		this.numberOfReviews = numReviews;
		this.averageRating = avgRating;
	}
	
	public Integer getSchoolInfoId() {
		return this.schoolInfoId;
	}

	public void setSchoolInfoId(Integer schoolInfo) {
		this.schoolInfoId = schoolInfo;
	}

	public String getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(String school) {
		this.schoolId = school;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double lat) {
		this.latitude = lat;
	}
	
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double lon) {
		this.longitude = lon;
	}

	public String getCity() {
		return this.city;
	}
	
	public void setCity(String cityStr) {
		this.city = cityStr;
	}
	
	public String getState() {
		return this.state;
	}

	public void setState(String stateStr) {
		this.state = stateStr;
	}
	
	public Integer getZip() {
		return this.zip;
	}

	public void setZip(Integer zipStr) {
		this.zip = zipStr;
	}
	
	public Integer getNumberOfReviews() {
		return this.numberOfReviews;
	}

	public void setNumberOfReviews(Integer numReviews) {
		this.numberOfReviews = numReviews;
	}
	
	public Double getAverageRating() {
		return this.averageRating;
	}

	public void setAverageRating(Double avgRating) {
		this.averageRating = avgRating;
	}

	public int compareTo(SchoolInfo schoolInfo) {
		double avgrate = schoolInfo.getAverageRating();
		//descending order
		if((int) (avgrate*10 - this.averageRating*10) ==  0) {
			return 1;
		}
		return (int) (avgrate*10 - this.averageRating*10);
	}
}
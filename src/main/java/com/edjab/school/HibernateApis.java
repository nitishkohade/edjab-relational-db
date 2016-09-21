package com.edjab.school;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.edjab.model.InstituteIntroProfile;
import com.edjab.model.InstituteName;
import com.edjab.model.LocationName;
import com.edjab.persistence.HibernateUtil;

public class HibernateApis {
	
	@SuppressWarnings("unchecked")
	public List<InstituteName> getSchoolsByPrefix(final String schoolPrefix) {
		if(schoolPrefix == null || schoolPrefix.isEmpty()) {
			return null;
		}
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query qry = session.createQuery("select distinct sc.schoolId from SchoolInfo as sc where sc.schoolId like ? order by schoolId");
		qry.setFirstResult(0);
		qry.setMaxResults(10);
		qry.setString(0, "%"+schoolPrefix.toUpperCase()+"%");
		
		final List<String> schoolList = qry.list();
		
		List<InstituteName> schools = new ArrayList<InstituteName>();
		
		for(int i=0; i<schoolList.size(); i++) {
			InstituteName instituteName = new InstituteName();
			 final String sclInfo = schoolList.get(i);
			 instituteName.setInstituteId(sclInfo);
			 	schools.add(instituteName);
	   }
		return schools;
	}
	
	/*
	@SuppressWarnings("unchecked")
	public List<String> getLocations(final String locPrefix) {
		if(locPrefix == null || locPrefix.isEmpty()) {
			return null;
		}
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		List<String> locations = new ArrayList<String>();
		
		if(Character.isDigit(locPrefix.charAt(0))) {
			Query qry = session.createQuery("select distinct zip From SchoolInfo as sc where sc.zip like ? order by zip");
			qry.setFirstResult(0);
			qry.setMaxResults(10);
			qry.setString(0, "%"+locPrefix+"%");
		    
			locations = qry.list();
		}
		else {
		  Query qry1 = session.createQuery("select distinct city From SchoolInfo as sc where sc.city like ? order by city");
		  qry1.setFirstResult(0);
		  qry1.setMaxResults(10);
		  qry1.setString(0, "%"+locPrefix+"%");
		  
		  Query qry2 = session.createQuery("select distinct state From SchoolInfo as sc where sc.state like ? order by state");
		  qry2.setFirstResult(0);
		  qry2.setMaxResults(10);
		  qry2.setString(0, "%"+locPrefix+"%");

		  locations = qry1.list();
		  locations.addAll(qry2.list());
		}
		 Collections.<String>sort(locations);
		 
		return locations;
	}
	*/
	
	@SuppressWarnings("unchecked")
	public List<LocationName> getLocations(final String locPrefix) {
		if(locPrefix == null || locPrefix.isEmpty()) {
			return null;
		}
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		List<String> locations = new ArrayList<String>();
		
		if(Character.isDigit(locPrefix.charAt(0))) {
			Query qry = session.createQuery("select distinct zip From SchoolInfo as sc where sc.zip like ? order by zip");
			qry.setFirstResult(0);
			qry.setMaxResults(10);
			qry.setString(0, locPrefix+"%");
		    
			locations = qry.list();
		}
		else {
		  Query qry1 = session.createQuery("select distinct city From SchoolInfo as sc where sc.city like ? order by city");
		  qry1.setFirstResult(0);
		  qry1.setMaxResults(10);
		  qry1.setString(0, locPrefix+"%");
		  
		  Query qry2 = session.createQuery("select distinct state From SchoolInfo as sc where sc.state like ? order by state");
		  qry2.setFirstResult(0);
		  qry2.setMaxResults(10);
		  qry2.setString(0, locPrefix+"%");

		  locations = qry1.list();
		  locations.addAll(qry2.list());
		}
		 Collections.<String>sort(locations);
		 
		 List<LocationName> locationNames = new ArrayList<LocationName>();
			
			for(int i=0; i<locations.size(); i++) {
				LocationName locationName = new LocationName();
				 final Object locInfo = locations.get(i);
				 locationName.setLocName(locInfo.toString());
				 locationNames.add(locationName);
		   }
		return locationNames;
	}
	
	@SuppressWarnings("unchecked")
	public List<InstituteIntroProfile> getSchoolBylocation(final String locationId) {
		if(locationId == null || locationId.isEmpty()) {
			return null;
		}
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		List<InstituteIntroProfile> schools = new ArrayList<InstituteIntroProfile>();
		
		if(Character.isDigit(locationId.charAt(0))) {
			Query query = session.createQuery("from SchoolInfo as sc where sc.zip = :zipcode order by averageRating desc");
			query.setFirstResult(0);
			query.setMaxResults(100);
			query.setParameter("zipcode", Integer.parseInt(locationId));
		
			final List<SchoolInfo> schoolList = query.list();
			
			for(int i=0; i<schoolList.size(); i++) {
				 InstituteIntroProfile instituteIntroProfile = new InstituteIntroProfile();
				 final SchoolInfo sclInfo = schoolList.get(i);
				 	instituteIntroProfile.setInstituteId(sclInfo.getSchoolId());
				 	instituteIntroProfile.setLatitude(sclInfo.getLatitude());
				 	instituteIntroProfile.setLongitude(sclInfo.getLongitude());
				 	instituteIntroProfile.setAverageRating(sclInfo.getAverageRating());
				 	schools.add(instituteIntroProfile);
		   }
		}
		else if(locationId.contains("_")){
			Query query = session.createQuery("from SchoolInfo as sc where sc.city = :cityString order by averageRating desc");
			query.setFirstResult(0);
			query.setMaxResults(100);
			query.setParameter("cityString", locationId.toLowerCase());
		
			final List<SchoolInfo> schoolList = query.list();
			
			for(int i=0; i<schoolList.size(); i++) {
				 InstituteIntroProfile instituteIntroProfile = new InstituteIntroProfile();
				 final SchoolInfo sclInfo = schoolList.get(i);
				 	instituteIntroProfile.setInstituteId(sclInfo.getSchoolId());
				 	instituteIntroProfile.setLatitude(sclInfo.getLatitude());
				 	instituteIntroProfile.setLongitude(sclInfo.getLongitude());
				 	instituteIntroProfile.setAverageRating(sclInfo.getAverageRating());
				 	schools.add(instituteIntroProfile);
		   }
		}
		else {
			Query query = session.createQuery("from SchoolInfo as sc where sc.state = :stateString order by averageRating desc");
			query.setFirstResult(0);
			query.setMaxResults(100);
			query.setParameter("stateString", locationId.toLowerCase());
		
			final List<SchoolInfo> schoolList = query.list();
			
			for(int i=0; i<schoolList.size(); i++) {
				 InstituteIntroProfile instituteIntroProfile = new InstituteIntroProfile();
				 final SchoolInfo sclInfo = schoolList.get(i);
				 	instituteIntroProfile.setInstituteId(sclInfo.getSchoolId());
				 	instituteIntroProfile.setLatitude(sclInfo.getLatitude());
				 	instituteIntroProfile.setLongitude(sclInfo.getLongitude());
				 	instituteIntroProfile.setAverageRating(sclInfo.getAverageRating());
				 	schools.add(instituteIntroProfile);
		   }
		}
		return schools;
	}
	
	@SuppressWarnings("unchecked")
	public List<InstituteIntroProfile> getSchoolByCategory(final String categoryId) {
		if(categoryId == null || categoryId.isEmpty()) {
			return null;
		}
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		List<InstituteIntroProfile> schools = new ArrayList<InstituteIntroProfile>();
		
		Query query = session.createQuery("select sc from SchoolCategoryInfo as sci, SchoolInfo as sc where sc.schoolId = sci.schoolId and sci.categoryName = :catName order by sc.averageRating desc");
		query.setFirstResult(0);
		query.setMaxResults(100);
		query.setParameter("catName", categoryId.toUpperCase());
		final List<SchoolInfo> schoolList = query.list();
		
		for(int i=0; i<schoolList.size(); i++) {
			 InstituteIntroProfile instituteIntroProfile = new InstituteIntroProfile();
			 final SchoolInfo sclInfo = schoolList.get(i);
			 	instituteIntroProfile.setInstituteId(sclInfo.getSchoolId());
			 	instituteIntroProfile.setLatitude(sclInfo.getLatitude());
			 	instituteIntroProfile.setLongitude(sclInfo.getLongitude());
			 	instituteIntroProfile.setAverageRating(sclInfo.getAverageRating());
			 	schools.add(instituteIntroProfile);
	   }
		
		return schools;
	}
	
	@SuppressWarnings("unchecked")
	public List<InstituteIntroProfile> getTopRatedSchools() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<InstituteIntroProfile> schools = new ArrayList<InstituteIntroProfile>();
			Query qry = session.createQuery("From SchoolInfo as sc order by averageRating desc");
			qry.setFirstResult(0);
			qry.setMaxResults(100);
			final List<SchoolInfo> schoolList = qry.list();
			
			for(int i=0; i<schoolList.size(); i++) {
					 InstituteIntroProfile instituteIntroProfile = new InstituteIntroProfile();
					 final SchoolInfo sclInfo = schoolList.get(i);
					 	instituteIntroProfile.setInstituteId(sclInfo.getSchoolId());
					 	instituteIntroProfile.setLatitude(sclInfo.getLatitude());
					 	instituteIntroProfile.setLongitude(sclInfo.getLongitude());
					 	instituteIntroProfile.setAverageRating(sclInfo.getAverageRating());
					 	schools.add(instituteIntroProfile);
			   }
		return schools;
	}
	

	@SuppressWarnings("unchecked")
	public List<InstituteIntroProfile> getSchoolByCategoryAndLocation(final String category, 
			final String location) {
		if(category == null || category.isEmpty()) {
			return null;
		}
		if(location == null || location.isEmpty()) {
			return null;
		}
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
        
		List<InstituteIntroProfile> schools = new ArrayList<InstituteIntroProfile>();
		
		if(Character.isDigit(location.charAt(0))) {
			Query query = session.createQuery("select sc from SchoolCategoryInfo as sci, SchoolInfo as sc where sc.schoolId = sci.schoolId and sc.zip = :zipcode and sci.categoryName = :catName order by sc.averageRating desc");
			query.setFirstResult(0);
			query.setMaxResults(100);
			query.setParameter("catName", category.toUpperCase());
			query.setParameter("zipcode", Integer.parseInt(location));
			
			final List<SchoolInfo> schoolList = query.list();
			
			for(int i=0; i<schoolList.size(); i++) {
				 InstituteIntroProfile instituteIntroProfile = new InstituteIntroProfile();
				 final SchoolInfo sclInfo = schoolList.get(i);
				 	instituteIntroProfile.setInstituteId(sclInfo.getSchoolId());
				 	instituteIntroProfile.setLatitude(sclInfo.getLatitude());
				 	instituteIntroProfile.setLongitude(sclInfo.getLongitude());
				 	instituteIntroProfile.setAverageRating(sclInfo.getAverageRating());
				 	schools.add(instituteIntroProfile);
		   }
		}
		else if(location.contains("_")){
			Query query = session.createQuery("select sc from SchoolCategoryInfo as sci, SchoolInfo as sc where sc.schoolId = sci.schoolId and sc.city = :cityString and sci.categoryName = :catName order by sc.averageRating desc");
			query.setFirstResult(0);
			query.setMaxResults(100);
			query.setParameter("catName", category.toUpperCase());
			query.setParameter("cityString", location.toLowerCase());
		
			final List<SchoolInfo> schoolList = query.list();
			
			for(int i=0; i<schoolList.size(); i++) {
				 InstituteIntroProfile instituteIntroProfile = new InstituteIntroProfile();
				 final SchoolInfo sclInfo = schoolList.get(i);
				 	instituteIntroProfile.setInstituteId(sclInfo.getSchoolId());
				 	instituteIntroProfile.setLatitude(sclInfo.getLatitude());
				 	instituteIntroProfile.setLongitude(sclInfo.getLongitude());
				 	instituteIntroProfile.setAverageRating(sclInfo.getAverageRating());
				 	schools.add(instituteIntroProfile);
		   }
		}
		else {
			Query query = session.createQuery("select sc from SchoolCategoryInfo as sci, SchoolInfo as sc where sc.schoolId = sci.schoolId and sc.state = :stateString and sci.categoryName = :catName order by sc.averageRating desc");
			query.setFirstResult(0);
			query.setMaxResults(100);
			query.setParameter("catName", category.toUpperCase());
			query.setParameter("stateString", location.toLowerCase());
		
			final List<SchoolInfo> schoolList = query.list();
			
			for(int i=0; i<schoolList.size(); i++) {
				 InstituteIntroProfile instituteIntroProfile = new InstituteIntroProfile();
				 final SchoolInfo sclInfo = schoolList.get(i);
				 	instituteIntroProfile.setInstituteId(sclInfo.getSchoolId());
				 	instituteIntroProfile.setLatitude(sclInfo.getLatitude());
				 	instituteIntroProfile.setLongitude(sclInfo.getLongitude());
				 	instituteIntroProfile.setAverageRating(sclInfo.getAverageRating());
				 	schools.add(instituteIntroProfile);
		   }
		}
		return schools;
	}
	
	public List<InstituteIntroProfile> getNearByTopRatedSchools(final int radius, final double latitude, 
			final double longitude) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		
		double resultantLongitude1 = longitude - radius / Math.abs(Math.cos(latitude)*69);
		double resultantLongitude2 = longitude + radius / Math.abs(Math.cos(latitude)*69);
		double resultantLatitude1 = latitude - (radius/69);
		double resultantLatitude2 = latitude + (radius/69);
		
		Query query = session.createQuery("select sc from SchoolInfo sc "
				+ "where sc.longitude between :lon1 and :lon2 "
				+ "and sc.latitude between :lat1 and :lat2  "
				+ "order by sc.averageRating desc");
		
		query.setFirstResult(0);
		query.setMaxResults(100);
		query.setParameter("lat1", resultantLatitude1);
		query.setParameter("lat2", resultantLatitude2);
		query.setParameter("lon1", resultantLongitude1);
		query.setParameter("lon2", resultantLongitude2);
		
		@SuppressWarnings("unchecked")
		final List<SchoolInfo> schoolList = query.list();
		
		List<InstituteIntroProfile> schools = new ArrayList<InstituteIntroProfile>();
		for(int i=0; i<schoolList.size(); i++) {
			 InstituteIntroProfile instituteIntroProfile = new InstituteIntroProfile();
			 final SchoolInfo sclInfo = schoolList.get(i);
			 	instituteIntroProfile.setInstituteId(sclInfo.getSchoolId());
			 	instituteIntroProfile.setLatitude(sclInfo.getLatitude());
			 	instituteIntroProfile.setLongitude(sclInfo.getLongitude());
			 	instituteIntroProfile.setAverageRating(sclInfo.getAverageRating());
			 	schools.add(instituteIntroProfile);
	   }
		return schools;
	}
	
	public List<InstituteIntroProfile> getNearByTopRatedSchoolsByCategory(final int radius, final double latitude, 
			final double longitude, final String category) {
		
		if(category == null || category.isEmpty()) {
			return null;
		}
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		
		double resultantLongitude1 = longitude - radius / Math.abs(Math.cos(latitude)*69);
		double resultantLongitude2 = longitude + radius / Math.abs(Math.cos(latitude)*69);
		double resultantLatitude1 = latitude - (radius/69);
		double resultantLatitude2 = latitude + (radius/69);
		
		Query query = session.createQuery("select sc from SchoolCategoryInfo as sci, SchoolInfo as sc "
				+ "where sc.longitude between :lon1 and :lon2 "
				+ "and sc.latitude between :lat1 and :lat2 "
				+ "and sc.schoolId = sci.schoolId and sci.categoryName = :catName "
				+ "order by sc.averageRating desc");
		
		query.setFirstResult(0);
		query.setMaxResults(100);
		query.setParameter("lat1", resultantLatitude1);
		query.setParameter("lat2", resultantLatitude2);
		query.setParameter("lon1", resultantLongitude1);
		query.setParameter("lon2", resultantLongitude2);
		query.setParameter("catName", category.toUpperCase());
		
		@SuppressWarnings("unchecked")
		final List<SchoolInfo> schoolList = query.list();
		
		List<InstituteIntroProfile> schools = new ArrayList<InstituteIntroProfile>();
		for(int i=0; i<schoolList.size(); i++) {
			 InstituteIntroProfile instituteIntroProfile = new InstituteIntroProfile();
			 final SchoolInfo sclInfo = schoolList.get(i);
			 	instituteIntroProfile.setInstituteId(sclInfo.getSchoolId());
			 	instituteIntroProfile.setLatitude(sclInfo.getLatitude());
			 	instituteIntroProfile.setLongitude(sclInfo.getLongitude());
			 	instituteIntroProfile.setAverageRating(sclInfo.getAverageRating());
			 	schools.add(instituteIntroProfile);
	   }
		return schools;
	}
	
	public int updateAverageRating(final String school, final int starRating) {
		if(school == null || school.isEmpty()) {
			return -1;
		}
		if(starRating < 0 || starRating > 5) {
			return -1;
		}
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("update SchoolInfo set averageRating = "
				+ "((averageRating*numberOfReviews)+:starRate)/(numberOfReviews+1), numberOfReviews = numberOfReviews+1"
				+ "where schoolId = :schoolName");
		query.setParameter("starRate", Double.valueOf(starRating));
		query.setParameter("schoolName", school.toUpperCase());
		return query.executeUpdate();
	}
	
	public int createSchoolProfile(final SchoolInfo schoolInfo) {
		try {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(schoolInfo);
        session.getTransaction().commit();
		}
		catch(Exception exception) {
			return -1;
		}
		return 0;
	}
	
	public int createSchoolCategoryProfile(final SchoolCategoryInfo schoolCategoryInfo) {
	try {
          Session session = HibernateUtil.getSessionFactory().openSession();
          session.beginTransaction();
          session.save(schoolCategoryInfo);
          session.getTransaction().commit();
	    }
		catch(HibernateException hibernateException) {
			return -1;
		}
		return 0;
	}
	
	 public static void main( String[] args )
	    {
	    	System.out.println("Maven + Hibernate + MySQL");
	        //System.out.println(new HibernateApis().getSchoolsByPrefix("in"));
	        //System.out.println(new HibernateApis().getLocations("m"));
	        //System.out.println(new HibernateApis().getSchoolBylocation("uttarakhand"));
	    	//System.out.println(new HibernateApis().getSchoolByCategory("engineering"));
	    	//System.out.println(new HibernateApis().getSchoolByCategoryAndLocation("engineering", "mumb"));
	    	//System.out.println(new HibernateApis().getTopRatedSchools());
	    	//System.out.println(new HibernateApis().updateAverageRating("INDIAN_INSTITUTE_OF_TECHNOLOGY_ROORKEE_UTTARAKHAND1", 4));
	        SchoolInfo schoolInfo = new SchoolInfo();
	        schoolInfo.setSchoolId("INDIAN_INSTITUTE_OF_TECHNOLOGY_KHARAGPUR_WEST-BENGAL");
	        schoolInfo.setLatitude(22.3149274);
	        schoolInfo.setLongitude(87.3105311);
	        schoolInfo.setCity("KHARAGPUR_WB");
	        schoolInfo.setState("WEST BENGAL");
	        schoolInfo.setZip(721302);
	        schoolInfo.setAverageRating(0.0);
	        schoolInfo.setNumberOfReviews(0);
	        //System.out.println(new HibernateApis().createSchoolProfile(schoolInfo));
	        //System.out.println(getLocations("roo"));
	    	SchoolCategoryInfo schoolCategoryInfo = new SchoolCategoryInfo();
	    	schoolCategoryInfo.setCategoryName("ARCHITECTURE");
	    	schoolCategoryInfo.setSchoolId("INDIAN_INSTITUTE_OF_TECHNOLOGY_KHARAGPUR_WEST-BENGAL");
	    	System.out.println(new HibernateApis().createSchoolCategoryProfile(schoolCategoryInfo));
	    	//System.out.println(new HibernateApis().getNearByTopRatedSchools(80, 29.00, 77.00));
	    	//System.out.println(new HibernateApis().getNearByTopRatedSchoolsByCategory(80, 29.00, 77.00, "ENGINEERING"));
	    }
}

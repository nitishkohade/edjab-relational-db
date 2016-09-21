package com.edjab.school;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.edjab.persistence.HibernateUtil;

public class App 
{
	public static List<String> getLocations(final String prefix) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query qry = session.createQuery("select distinct city From SchoolInfo as rb where rb.city like ?");
		qry.setString(0, "%"+prefix+"%");
		return qry.list();
	}
	
    public static void main( String[] args )
    {
    	System.out.println("Maven + Hibernate + MySQL");
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        session.beginTransaction();
        SchoolInfo schoolInfo = new SchoolInfo();
        
        schoolInfo.setSchoolId("INDIAN_INSTITUTE_OF_TECHNOLOGY_ROORKEE_UTTARAKHAND4");
        schoolInfo.setLatitude(29.862813);
        schoolInfo.setLongitude(77.897582);
        schoolInfo.setCity("mumbai");
        schoolInfo.setState("maharashtra");
        schoolInfo.setZip(247667);
        schoolInfo.setAverageRating(3.5);
        schoolInfo.setNumberOfReviews(4);
        
        session.save(schoolInfo);
        session.getTransaction().commit();
        //System.out.println(getLocations("roo"));
        
    }
}

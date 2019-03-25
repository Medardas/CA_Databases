package com.codeacademy.hibernatetutorial;

import com.codeacademy.hibernatetutorial.model.Company;
import com.codeacademy.hibernatetutorial.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class H2DBExample {

    public static void main(String[] theory) throws InterruptedException {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        Company foundCompany = session.find(Company.class, 1);
        System.out.println("Company name: " + foundCompany.getName());

        sessionFactory.close();
    }

}

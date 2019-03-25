package com.codeacademy.hibernatetutorial.util;

import com.codeacademy.hibernatetutorial.model.*;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private static Logger logger = Logger.getLogger(HibernateUtil.class);

    private static SessionFactory buildSessionFactory(String config) {
        try {
            Configuration configuration = new Configuration()
                    .addAnnotatedClass(Company.class)
                    .addAnnotatedClass(Employee.class)
                    .addAnnotatedClass(Contact.class)
                    .addAnnotatedClass(Address.class)
                    .addAnnotatedClass(Person.class)
                    .addAnnotatedClass(Salary.class)
                    .addAnnotatedClass(Branch.class);
            configuration.configure(config == null ? "hibernate.cfg.xml" : config);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        } catch (Throwable ex) {
            logger.error("Initial SessionFactory creation failed." + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(String config) {
        if (sessionFactory == null) sessionFactory = buildSessionFactory(config);
        return sessionFactory;
    }
}

package com.codeacademy.hibernatetutorial;


import com.codeacademy.hibernatetutorial.logger.LoggerCreator;
import com.codeacademy.hibernatetutorial.model.Company;
import com.codeacademy.hibernatetutorial.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;

import static com.codeacademy.hibernatetutorial.logger.AppLoggerLevel.APPLOGGER;

public class FirstLevelCachingExample {
    private static Logger logger = LoggerCreator.buildLogger(FirstLevelCachingExample.class);
    private static SessionFactory sessionFactory = HibernateUtil.buildSessionFactory("hibernate.cfg.FirstLevelCachingExample.xml");

    public static void main(String[] theory) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        //Pirmą kartą paleidus užklausa ji turi nukeliauti iki duombazės
        long startTime = System.currentTimeMillis();
        Company company1 = session.find(Company.class, 1);
        logger.log(APPLOGGER, "Company with id: " + company1.getId() + " found.");
        logger.log(APPLOGGER, "First time query took: " + (System.currentTimeMillis() - startTime));

        //Antrą karta hibernate pamato, kad ieškoma to pačio entity todėl jį gražina iš 1st level cache. Dėl to nepamatysis loguose, kad buvo išsiųsta užklausa
        long startTime2 = System.currentTimeMillis();
        Company company2 = session.find(Company.class, 1);
        logger.log(APPLOGGER, "Company with id: " + company2.getId() + " found.");
        logger.log(APPLOGGER, "Second time query took:" + (System.currentTimeMillis() - startTime2));

        //Čia ieškoma naujos kompanijos, todėl užklausa vėl nukeliauja iki duombazės
        long startTime3 = System.currentTimeMillis();
        Company company3 = session.find(Company.class, 2);
        logger.log(APPLOGGER, "Company with id: " + company3.getId() + " found.");
        logger.log(APPLOGGER, "Third time query took:" + (System.currentTimeMillis() - startTime3));

        //Ši funkcija ištrina objektą iš pirmo lygio kešo
        session.evict(company3);

        //Nors čia ieškom to pačio entity, jis buvo ištrintas (42 eilutėje) iš pirmo lygio kešo todėl užklausa vėl iškeliavo iki duombazės
        long startTime4 = System.currentTimeMillis();
        Company company4 = session.find(Company.class, 2);
        logger.log(APPLOGGER, "Company with id: " + company4.getId() + " found.");
        logger.log(APPLOGGER, "Fourth time query took:" + (System.currentTimeMillis() - startTime4));

        transaction.commit();
        session.close();
    }

}
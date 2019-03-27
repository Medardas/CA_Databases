package com.codeacademy.hibernatetutorial;


import com.codeacademy.hibernatetutorial.logger.LoggerCreator;
import com.codeacademy.hibernatetutorial.model.Company;
import com.codeacademy.hibernatetutorial.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;

import static com.codeacademy.hibernatetutorial.logger.AppLoggerLevel.APPLOGGER;

public class FirstLevelCachingExample {
    private static Logger logger = LoggerCreator.buildLogger(FirstLevelCachingExample.class);
    private static Session session = HibernateUtil.buildSessionFactory("hibernate.cfg.FirstLevelCachingExample.xml").openSession();

    public static void main(String[] theory) throws Exception {
        Statistics stats = session.getSessionFactory().getStatistics();
        stats.setStatisticsEnabled(true);

        long startTime = System.currentTimeMillis();
        Company company1 = session.load(Company.class, 1);
        logger.log(APPLOGGER, "Company with id: " + company1.getId() + " found.");
        logger.log(APPLOGGER, "First time query took: " + (System.currentTimeMillis() - startTime));

        long startTime2 = System.currentTimeMillis();
        Company company2 = session.load(Company.class, 1);
        logger.log(APPLOGGER, "Company with id: " + company2.getId() + " found.");
        logger.log(APPLOGGER, "Second time query took:" + (System.currentTimeMillis() - startTime2));

        long startTime3 = System.currentTimeMillis();
        Company company3 = session.load(Company.class, 2);
        logger.log(APPLOGGER, "Company with id: " + company3.getId() + " found.");
        logger.log(APPLOGGER, "Third time query took:" + (System.currentTimeMillis() - startTime3));

        session.evict(company3);

        long startTime4 = System.currentTimeMillis();
        Company company4 = session.load(Company.class, 2);
        logger.log(APPLOGGER, "Company with id: " + company4.getId() + " found.");
        logger.log(APPLOGGER, "Fourth time query took:" + (System.currentTimeMillis() - startTime4));

        session.close();
    }

}
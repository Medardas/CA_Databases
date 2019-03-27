package com.codeacademy.hibernatetutorial;

import com.codeacademy.hibernatetutorial.logger.LoggerCreator;
import com.codeacademy.hibernatetutorial.model.Employee;
import com.codeacademy.hibernatetutorial.util.HibernateUtil;
import com.codeacademy.hibernatetutorial.util.Utils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

public class SecondLevelCacheExample {

    private static Logger logger = LoggerCreator.buildLogger(SecondLevelCacheExample.class);
    private static SessionFactory sessionFactory = HibernateUtil.buildSessionFactory("hibernate.cfg.SecondLevelCacheExample.xml");

    public static void main(String[] args) {

        Statistics stats = sessionFactory.getStatistics();
        stats.setStatisticsEnabled(true);

        Session session = sessionFactory.openSession();
        Session otherSession = sessionFactory.openSession();

        Utils.printStats(logger, stats, 0);

        Employee emp = session.load(Employee.class, 1);
        Utils.printData(logger, emp, stats, 1);

        emp = session.load(Employee.class, 1);
        Utils.printData(logger, emp, stats, 2);

        session.evict(emp);
        emp = session.load(Employee.class, 1);
        Utils.printData(logger, emp, stats, 3);

        emp = session.load(Employee.class, 2);
        Utils.printData(logger, emp, stats, 4);

        emp = otherSession.load(Employee.class, 1);
        Utils.printData(logger, emp, stats, 5);

        sessionFactory.close();
    }

}

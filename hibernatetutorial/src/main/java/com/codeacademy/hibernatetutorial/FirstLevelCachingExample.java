package com.codeacademy.hibernatetutorial;


import com.codeacademy.hibernatetutorial.model.Address;
import com.codeacademy.hibernatetutorial.model.Company;
import com.codeacademy.hibernatetutorial.model.Employee;
import com.codeacademy.hibernatetutorial.util.EntityFinder;
import com.codeacademy.hibernatetutorial.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import static com.codeacademy.hibernatetutorial.util.EntityCreator.*;

public class FirstLevelCachingExample {

    private static final int HOW_MANY = 100000;
    static Session session = HibernateUtil.getSessionFactory(null).openSession();

    public static void main(String[] theory) throws Exception {
        //     createNEmployees(session, HOW_MANY);


        Employee employee = createEmployeeInCompany();
        Statistics stats = session.getSessionFactory().getStatistics();
        stats.setStatisticsEnabled(true);

        long startTime = System.currentTimeMillis();
        Employee employee1 = EntityFinder.findEmployeeByName(session, "qclykwrsyddmicn");
        System.out.println("Employee with id: " + employee1.getId() + " found.");
        System.out.println("First time query took: " + milisToTime(System.currentTimeMillis() - startTime));

        long startTime2 = System.currentTimeMillis();
        Employee employee2 = EntityFinder.findEmployeeByName(session, "qclykwrsyddmicn");
        System.out.println("Employee with id: " + employee2.getId() + " found.");
        System.out.println("Second time query took:" + milisToTime(System.currentTimeMillis() - startTime2));

        long startTime3 = System.currentTimeMillis();
        Address address = EntityFinder.findAddressByStreet(session, "iwwyvkjovtsmipq");
        System.out.println("Address with id: " + address.getId() + " found.");
        System.out.println("Third time query took:" + milisToTime(System.currentTimeMillis() - startTime3));

        long startTime4 = System.currentTimeMillis();
        Address address2 = EntityFinder.findAddressByStreet(session, "iwwyvkjovtsmipq");
        System.out.println("Address with id: " + address2.getId() + " found.");
        System.out.println("Fourth time query took:" + milisToTime(System.currentTimeMillis() - startTime4));

        session.close();
    }

    private static void createNEmployees(Session session, int N) {
        clearEmployees(session, 0);
        Company company = getCompany(session);
        long startTime = System.currentTimeMillis();
        Set<Employee> employees = new HashSet<>();
        for (int i = 0; i < N; i++)
            employees.add(createEmployeeInCompany(/*company*/));
        session.getTransaction().begin();
        for (Employee e : employees)
            session.save(e);
        session.getTransaction().commit();
        System.out.println("Time took to enter " + N + " entries: " + milisToTime(System.currentTimeMillis() - startTime));
    }

    private static String milisToTime(long milis) {
        Date date = new Date(milis);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

}
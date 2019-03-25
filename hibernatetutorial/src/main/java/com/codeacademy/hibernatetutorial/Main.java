package com.codeacademy.hibernatetutorial;


import com.codeacademy.hibernatetutorial.model.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.Statistics;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.codeacademy.hibernatetutorial.util.EntityCreator.*;

public class Main {

    private static final int HOW_MANY = 100000;
    static Session session = createAndOpenSession();

    public static void main(String[] theory) throws Exception {
        //    createNEmployees(HOW_MANY);

        Employee employee = createEmployeeInCompany();
        Statistics stats = session.getSessionFactory().getStatistics();
        stats.setStatisticsEnabled(true);

        /*Contact contact = new Contact();
        contact.setContact_type("mobile phone");
        contact.setValue("370");
        contact.setEmployee(employee);
*/
        //employee.getContacts().add(contact);
        session.getTransaction().begin();
        session.save(employee);

        Employee employee2 = findEmployeeByName("wfgwtpqmmivbamm");

        Map cacheEntries = stats.getSecondLevelCacheStatistics("employee")
                .getEntries();

        session.getTransaction().commit();

        System.out.println("cache entries: " + cacheEntries.get(0));
        /*long startTime = System.currentTimeMillis();

        System.out.println("Employee with id: " + employee.getId() + " found.");
        System.out.println("First time query took: " + milisToTime(System.currentTimeMillis() - startTime));

        long startTime2 = System.currentTimeMillis();
        Employee employee2 = findEmployeeByName("jchuegecinlyjcc");
        System.out.println("Employee with id: " + employee2.getId() + " found.");
        System.out.println("Second time query took:" + milisToTime(System.currentTimeMillis() - startTime2));

        long startTime3 = System.currentTimeMillis();
        Address address = findAddressByStreet("jtpbbbobxxsvgtk");
        System.out.println("Employee with id: " + address.getId() + " found.");
        System.out.println("Third time query took:" + milisToTime(System.currentTimeMillis() - startTime3));

        long startTime4 = System.currentTimeMillis();
        Address address2 = findAddressByStreet("jtpbbbobxxsvgtk");
        System.out.println("Employee with id: " + address2.getId() + " found.");
        System.out.println("Fourth time query took:" + milisToTime(System.currentTimeMillis() - startTime4));*/

        session.close();
    }

    private static Employee findEmployeeByName(String name) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaFindByName = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaFindByName.from(Employee.class);
        criteriaFindByName.where(criteriaBuilder.equal(root.get("name"), name));
        return session.createQuery(criteriaFindByName).getSingleResult();
    }

    private static Address findAddressByStreet(String street) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaFindByStreet = criteriaBuilder.createQuery(Address.class);
        Root<Address> root = criteriaFindByStreet.from(Address.class);
        criteriaFindByStreet.where(criteriaBuilder.equal(root.get("street"), street));
        return session.createQuery(criteriaFindByStreet).getSingleResult();
    }

    private static void createNEmployees(Session session, int N) {
        clearEmployees(session, 0);
        Company company = getCompany(session);
        long startTime = System.currentTimeMillis();
        Set<Employee> employees = new HashSet<>();
        for (int i = 0; i < N; i++)
            employees.add(createEmployeeInCompany(/*company*/));
        session.getTransaction().begin();
        session.save(employees);
        session.getTransaction().commit();
        System.out.println("Time took to enter " + N + " entries: " + milisToTime(System.currentTimeMillis() - startTime));
    }

    private static String milisToTime(long milis) {
        Date date = new Date(milis);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }



    private static Session createAndOpenSession() {
        Configuration hibernateCfg = new Configuration()
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Contact.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Salary.class)
                .addAnnotatedClass(Branch.class)
                .configure();

        return hibernateCfg.buildSessionFactory().openSession();
    }

    private static void printByCriteria() {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        criteriaQuery.where(criteriaBuilder.equal(employeeRoot.get("name"), "Jooohn"));
        List<Employee> employees = session.createQuery(criteriaQuery).getResultList();
        for (Employee e : employees)
            System.out.println(e.getName());
    }
}
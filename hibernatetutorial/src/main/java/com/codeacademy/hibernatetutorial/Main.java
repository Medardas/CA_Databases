package com.codeacademy.hibernatetutorial;


import com.codeacademy.hibernatetutorial.model.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private static final String COMPANY_1 = "PROGRAMUOK";
    private static final String BRANCH_1 = "Sunrise valley";
    private static final String BRANCH_2 = "Tech Park";
    private static final int HOW_MANY = 100000;
    static Session session = createAndOpenSession();

    public static void main(String[] theory) throws Exception {
        //    createNEmployees(HOW_MANY);


        long startTime = System.currentTimeMillis();
        Employee employee = findEmployeeByName("jchuegecinlyjcc");
        System.out.println("Employee with id: " + employee.getId() + " found.");
        System.out.println("First time query took: " + milisToTime(System.currentTimeMillis() - startTime));

        long startTime2 = System.currentTimeMillis();
        Employee employee2 = findEmployeeByName("jchuegecinlyjcc");
        System.out.println("Employee with id: " + employee2.getId() + " found.");
        System.out.println("Second time query took:" + milisToTime(System.currentTimeMillis() - startTime2));

        long startTime3 = System.currentTimeMillis();
        Address employee3 = findAddressByStreet("jtpbbbobxxsvgtk");
        System.out.println("Employee with id: " + employee3.getId() + " found.");
        System.out.println("Third time query took:" + milisToTime(System.currentTimeMillis() - startTime3));

        long startTime4 = System.currentTimeMillis();
        Address employee4 = findAddressByStreet("jtpbbbobxxsvgtk");
        System.out.println("Employee with id: " + employee4.getId() + " found.");
        System.out.println("Fourth time query took:" + milisToTime(System.currentTimeMillis() - startTime4));

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

    private static void createNEmployees(int N) {
        clearEmployees(0);
        Company company = getCompany();
        long startTime = System.currentTimeMillis();
        session.getTransaction().begin();
        for (int i = 0; i < N; i++)
            session.save(createEmployeeInCompany(company));
        session.getTransaction().commit();
        System.out.println("Time took to enter " + N + " entries: " + milisToTime(System.currentTimeMillis() - startTime));
    }

    private static String milisToTime(long milis) {
        Date date = new Date(milis);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    private static void clearEmployees(int deleteFromIds) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaDelete<Employee> criteriaDelete = criteriaBuilder.createCriteriaDelete(Employee.class);
        Root<Employee> root = criteriaDelete.from(Employee.class);
        criteriaDelete.where(criteriaBuilder.greaterThanOrEqualTo(root.get("id"), deleteFromIds));
        session.getTransaction().begin();
        session.createQuery(criteriaDelete).executeUpdate();
        session.getTransaction().commit();
    }

    private static Company getCompany() {
        Company company = findByName(COMPANY_1);
        if (company == null) {
            company = createCompany(COMPANY_1);
            save(company);
        }
        return company;
    }

    private static Employee createEmployeeInCompany(Company company) {
        Employee employee = new Employee();
        employee.setCompany(company);
        Address address = new Address();
        address.setStreet(randomString());
        address.setCity("Vilnius");
        employee.setAddress(address);
        employee.setPosition("Developer");
        employee.setName(randomString());
        return employee;
    }

    private static void save(Object o) {
        session.getTransaction().begin();
        session.save(o);
        session.getTransaction().commit();
    }

    private static Company createCompany(String companyName) {
        Address companyAddress = new Address();
        companyAddress.setCity("Vilnius");
        companyAddress.setStreet("Sunrise valley");

        Company company = new Company();
        company.setName(companyName);
        company.setAddress(companyAddress);

        return company;
    }

    private static Company findByName(String companyName) {
        Query companyByName = session.createQuery("from Company c where c.name=:companyName");
        companyByName.setParameter("companyName", companyName);
        try {
            return (Company) companyByName.list().stream().findFirst().get();
        } catch (RuntimeException re) {
            return null;
        }
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

    private static String randomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 15;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    private static void saveCompanyWIth2Branches() {
        Company company = new Company();
        company.setName(COMPANY_1);

        company.setBranches(new HashSet<>());
        Branch branch = new Branch(BRANCH_1);
        branch.setCompany(company);
        Branch branch2 = new Branch(BRANCH_2);
        branch2.setCompany(company);
        company.getBranches().addAll(List.of(branch, branch2));

        session.save(company);
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
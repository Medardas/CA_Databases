package com.codeacademy;

import com.codeacademy.model.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] theory) {
        Configuration hibernateCfg = new Configuration()
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Contact.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Salary.class)
                .configure();

        Session session = hibernateCfg.buildSessionFactory().openSession();
        session.getTransaction().begin();

        Person person = new Person();
        person.setName("Petras");
        person.setAge(30);
        session.save(person);
        Salary salary = new Salary();
        salary.setPay(1000);
        salary.setPersonId(person.getId());
        session.save(salary);

       /* Address companyAddress = new Address();
        companyAddress.setCity("Kaunas");
        companyAddress.setStreet("Laisves pr.");

        Employee employee = new Employee();
        employee.setName("John");
        employee.setPosition("Developer");

        Company company = new Company();
        company.setName("Some Company name");
        company.setAddress(companyAddress);

        Address employeeAddress = new Address();
        employeeAddress.setCity("Vilnius");
        employeeAddress.setStreet("Gedo pr.");


        employee.setCompany(company);
        employee.setAddress(employeeAddress);

        Contact contact = new Contact();
        contact.setContact_type("mobile_phone");
        contact.setValue("37000000000");
        contact.setEmployee(employee);

        session.save(contact);
        session.save(employee);
        session.save(company);*/

        session.getTransaction().commit();

        session.close();
    }

}
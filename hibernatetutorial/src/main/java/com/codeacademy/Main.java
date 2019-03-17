package com.codeacademy;

import com.codeacademy.model.Person;
import com.codeacademy.model.Salary;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] theory) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Configuration hibernateCfg = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Salary.class)
                .configure();

        Session session = hibernateCfg.buildSessionFactory().openSession();
        session.getTransaction().begin();

        Person person = new Person("Jonas", 31);
        session.save(person);
        Salary salary = new Salary(1234);
        salary.setId(person.getId());
        person.setSalary(salary);
        session.save(person);
        session.getTransaction().commit();


        System.out.println(person.getId());
        System.out.println(person.getSalary().getId());
        System.out.println(person.getSalary().getPay());
/*
        em.getTransaction().begin();
        System.out.println(em.find(Salary.class, salary.getPerson_id()));
        em.getTransaction().commit();
*/

        session.close();
    }

}
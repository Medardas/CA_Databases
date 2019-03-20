package com.codeacademy;


import com.codeacademy.model.*;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] theory) {
        Configuration hibernateCfg = new Configuration()
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Contact.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Salary.class)
                .addAnnotatedClass(Branch.class)
                .configure();

        Session session = hibernateCfg.buildSessionFactory().openSession();
        session.getTransaction().begin();

        Company company = new Company();
        company.setName("Some Company name");

        company.setBranches(new HashSet<>());
        Branch branch = new Branch("Some branch name");
        branch.setCompany(company);
        Branch branc2 = new Branch("Some other branch name");
        branch.setCompany(company);
        company.getBranches().addAll(List.of(branch, branc2));

        session.save(company);

        session.getTransaction().commit();


        Set<Branch> branches = session.find(Company.class, company.getId()).getBranches();
        for (Branch b : branches)
            System.out.println(b.getName());
/*
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        criteriaQuery.where(criteriaBuilder.equal(employeeRoot.get("name"), "Jooohn"));
        List<Employee> employees = session.createQuery(criteriaQuery).getResultList();
*/

        session.close();
    }

}
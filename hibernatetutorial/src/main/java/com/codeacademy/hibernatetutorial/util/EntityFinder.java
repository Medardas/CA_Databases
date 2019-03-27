package com.codeacademy.hibernatetutorial.util;

import com.codeacademy.hibernatetutorial.model.Address;
import com.codeacademy.hibernatetutorial.model.Employee;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class EntityFinder {

    /**
     * Randa darbuotoją pagal duotą vardą.
     *
     * @param session - sesija kurioje atlikti paiešką
     * @param name    - vardas pagal kurį ieškoti.
     * @return - rastą darbuotojo objektą
     */
    public static Employee findEmployeeByName(Session session, String name) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaFindByName = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaFindByName.from(Employee.class);
        criteriaFindByName.where(criteriaBuilder.equal(root.get("name"), name));
        return session.createQuery(criteriaFindByName).getSingleResult();
    }

    /**
     * Randa adresą pagal duotą gatvė.
     * @param session - sesija kurioje atlikti paiešką
     * @param street - gatvė pagal kurią ieškoti
     * @return - rastą adreso objektą
     */
    public static Address findAddressByStreet(Session session, String street) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaFindByStreet = criteriaBuilder.createQuery(Address.class);
        Root<Address> root = criteriaFindByStreet.from(Address.class);
        criteriaFindByStreet.where(criteriaBuilder.equal(root.get("street"), street));
        return session.createQuery(criteriaFindByStreet).getSingleResult();
    }

}

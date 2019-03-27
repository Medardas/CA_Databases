package com.codeacademy.hibernatetutorial.util;

import com.codeacademy.hibernatetutorial.model.Address;
import com.codeacademy.hibernatetutorial.model.Company;
import com.codeacademy.hibernatetutorial.model.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;

import static com.codeacademy.hibernatetutorial.util.Utils.random15CharString;

public class EntityCreator {
    private static final String COMPANY_1 = "PROGRAMUOK 1";
    private static final String BRANCH_1 = "Sunrise valley 1";
    private static final String BRANCH_2 = "Tech Park 1";

    /**
     * Sukuria darbuotoją su hardcodintu(reikšmės kurios yra statiškai įrašytos į kodą ir jų neįmanoma pakeisti dinamiškai) adresu.
     *
     * @return Employee objektą kuris turi adresą su hardcoded reikšmėmis.
     */
    public static Employee createEmployeeWithAddress() {
        Employee employee = new Employee();
        Address address = new Address();
        address.setStreet(random15CharString());
        address.setCity("Vilnius");
        employee.setAddress(address);
        employee.setPosition("Developer");
        employee.setName(random15CharString());
        return employee;
    }

    /**
     * Ištrina employees kurių ID didesnis arba lygus negu duotas.
     *
     * @param session       - sesijos objektas - perduodamas, nes ši utility funkcija neturi tiesioginio priėjimo prie sesijos
     * @param deleteFromIds - nusako nuo kurio id reikia ištrinti employees.
     */
    public static void clearEmployees(Session session, int deleteFromIds) {
        session.getTransaction().begin();
        CriteriaDelete<Employee> criteriaDelete = deleteFromId(session, Employee.class, deleteFromIds);
        session.createQuery(criteriaDelete).executeUpdate();
        session.getTransaction().commit();
    }

    /**
     * Ištrina adresus kurių ID didesnis arba lygus negu duotas.
     *
     * @param session       - sesijos objektas - perduodamas, nes ši utility funkcija neturi tiesioginio priėjimo prie sesijos
     * @param deleteFromIds nusako nuo kurio id reikia ištrinti adresus
     */
    public static void clearAddresses(Session session, int deleteFromIds) {
        session.getTransaction().begin();
        CriteriaDelete<Address> criteriaDelete = EntityCreator.deleteFromId(session, Address.class, deleteFromIds);
        session.createQuery(criteriaDelete).executeUpdate();
        session.getTransaction().commit();
    }

    /**
     * Sukuria kompanija su duotu pavadinimu
     *
     * @param companyName kompanijos pavadinimas
     * @return pprastas kompanijos objektas tik su pavadinimu
     */
    public static Company createCompany(String companyName) {
        Address companyAddress = new Address();
        companyAddress.setCity("Vilnius");
        companyAddress.setStreet("Sunrise valley");

        Company company = new Company();
        company.setName(companyName);
        company.setAddress(companyAddress);

        return company;
    }

    /**
     * Randa kompaniją pagal duotą pavadinima naudojant Hibernate Query Language (HQL)
     *
     * @param session     - sesijos objektas - perduodamas, nes ši utility funkcija neturi tiesioginio priėjimo prie sesijos
     * @param companyName -
     * @return
     */
    public static Company findByName(Session session, String companyName) {
        Query<Company> companyByName = session.createQuery("from Company c where c.name=:companyName");
        companyByName.setParameter("companyName", companyName);
        try {
            return companyByName.getSingleResult();
        } catch (RuntimeException re) {
            return null;
        }
    }

    /**
     * Randa kompaniją su hardcodintu pavadinimu
     *
     * @param session - sesijos objektas - perduodamas, nes ši utility funkcija neturi tiesioginio priėjimo prie sesijos
     * @return kompanijos objektą
     */
    public static Company getCompany(Session session) {
        Company company = findByName(session, COMPANY_1);
        if (company == null) {
            company = createCompany(COMPANY_1);
            save(session, company);
        }
        return company;
    }

    /**
     * Metodas išsaugantis duotą objektą ir kartu pasirupinantis transakcijos kurimu.
     *
     * @param session - sesijos objektas - perduodamas, nes ši utility funkcija neturi tiesioginio priėjimo prie sesijos
     * @param o       - objektas kurį reikia išsaugoti.
     */
    private static void save(Session session, Object o) {
        session.getTransaction().begin();
        session.save(o);
        session.getTransaction().commit();
    }

    /**
     * Sukuria "delete" kriterijų kuris trina visus duotus objektus kurių id didesnis arba lygus duotam.
     *
     * @param session             - sesijos objektas - perduodamas, nes ši utility funkcija neturi tiesioginio priėjimo prie sesijos
     * @param entityToDeleteClass - klasė objekto kurį norima ištrinti.
     * @param deleteFromIds       - id nuo kurio norima ištrinti
     * @param <T>                 java kompileris dinamiškai priskiria klasę objekto kurį reikia gražinti pagal kontekstą kur funkcija naudojama.
     * @return
     */
    private static <T> CriteriaDelete<T> deleteFromId(Session session,
                                                      Class entityToDeleteClass,
                                                      int deleteFromIds) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaDelete<T> criteriaDelete =
                criteriaBuilder.createCriteriaDelete(entityToDeleteClass);
        Root<T> root = criteriaDelete.from(entityToDeleteClass);
        criteriaDelete.where(criteriaBuilder
                .greaterThanOrEqualTo(root.get("id"), deleteFromIds));
        return criteriaDelete;
    }
}














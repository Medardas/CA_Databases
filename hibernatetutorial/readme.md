### Hibernate

 * Configuration
 * Entity
 * Entity Mapping
 * Persistence
 
 ### Configuration
 Gali būti daug konfiguracijos būdu:
 
 1. JPA konfiguracija. Ši konfiguracija skirta Java Persitance API, tačiau pakeitus `<provider>` tag'o reikšmę galima nustatinėti Hibernate konfiguraciją.  
 Tai padaryti classpath reikia turėti direktoriją/failą: META-INF/persistance.xml
    
```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <persistence xmlns="http://java.sun.com/xml/ns/persistence"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:schemaLocation="http://java.sun.com/xml/ns/persistence
     http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd" version="1.0">
    
       <persistence-unit name="my-pu">
         <description>My Persistence Unit</description>
         <provider>org.hibernate.ejb.HibernatePersistence</provider>
         <properties>
            <property name="hibernate.dialect" 
                                     value="org.hibernate.dialect.MySQLDialect"/>
            <property name="javax.persistence.jdbc.url"
                     value="jdbc:mysql://localhost:3306/playground"/>
            <property name="javax.persistence.jdbc.user" value="admin"/>
            <property name="javax.persistence.jdbc.password" value="admin"/>
         </properties>
       </persistence-unit>
    
    </persistence>
```
    
Tuomet jungiantis prie duombazės sukurti `Configuration` objektą iš kurio galima gauti `Session` objektą skirtą manipuliuoti `Entity` objektais duombazėje:
 ```java
Configuration config = new Configuration().configure(); //pridėti klases visu sukurtu Entities;
Session session = config.buildSessionFactory().getCurrentSession();
```
Komentare `//pridėti klases visu sukurtu Entities;` turima omeny pridėti visass sukurtas entities  klases per 'addAnnotatedClass(Class class)'
Pvz:
```java
Configuration config = new Configuration()
    .addAnnotatedClass(Person.class)
    .configure();
```
Atreipkite dėmesį, kad `configure()` pridedama po anotuotų klasių pridėjimo.

2. Kitas variantas yra turėti `hibernate.cfg.xml` failą savo classpath'e.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/playground?serverTimezone=UTC</property>
        <property name="hibernate.connection.username">medardas</property>
        <property name="hibernate.connection.password">pass</property>
        <property name="hibernate.connection.pool_size">1</property>
        <property name="hibernate.current_session_context_class">thread</property>
        <property name="hibernate.show_sql">true</property>
        <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>

    </session-factory>
</hibernate-configuration>
```
Čia yra standartinis Hibernate configuracijos variantas.
Sukurti `Session` galima lygiai tokiu pačiu būdu kaip priš tai.

### Entity

`Enitity` yra Java objektas kuris turi tiesioginį sąryšį savo field'ais su lentelės stulpeliais duombazėje.
Taigi jei tarkim egzistuoja lentelė:
```
\-------------------/
|       Person      |
---------------------
| ID |   Name | age |
--------------------
|  1 |  Jonas |  10 |
|  2 |   Ieva |  18 |
/-------------------\
```
Jos `Entity` bū java objektas su anotacija `@Entity`
```java
@Entity
@Data
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;
}
``` 
Čia Hibernate framework'as išsiaiškina kokios yra stulpelių reikšmės pagal tai kokie `Entity` objekte yra field'ų pavadinimai: `id`, `name`, `age`. Taip pat išsiaiškina stalo pavadinimą. 
   * `@Id` nusako, kad tai yra `PRIMARY KEY` reikšmė.
   * `@GeneratedValue(strategy = GenerationType.IDENTITY)` nusako, kad ši reikšmė yra auto-generuojama.
   
### Entity mapping
Viena sudėtingiausių dalių dirbant su hibernate yra teisingai sumappinti java objektus su lentlėmis.
Tačiau teisingai sukūrus šį sąryšį galima labai pasilengivnti darbą su savo sistema.
Taigi, jei egzituoja antra lentelė kur `PersonId` yra `Foreign Key` į `Person(id)`.
```
\------------------/
|        Salary    |
-------------------- 
| PersonId |  Pay |
--------------------
|         2 | 1000 | 
|         1 | 1200 | 
/------------------\
```

Kad ją galėtume surišti su `Person` objektu mūsų naujas `Entity` gali atrodyti taip:
```java

@Entity
@Data
@NoArgsConstructor
public class Salary {
    @Id
    private int personId;
    private int pay;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "personId", referencedColumnName = "id")
    private Person person;
}

```
O `Person` turėtų gauti nauja field'ą:
```java
@OneToOne(mappedBy = "person")
private Salary salary;
```

Šis detales hibernate supranta taip:
 * Hiberate žino, kad Person Primary key yra `Id`
 * Jis taip pat žino, kad primary key pas Salary yra `personId`
 * Sukurtas naujas field'as `salary` Objekte `Person` nurodo, kad `Person` turi `Salary` ir jų ryšys yra abiejų objektų Primary Key, tai yra `Person.id = Salary.personId`
 * `cascade = CascadeType.ALL` reiškia, kad jei rekšmė pasikeis šiame objekte, ji taip pat pasikeis ir `Person` objekte ir atitinkamais pakeitimais duombazėje bus pasirūpinta.

 
### Persistence

```java
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
        
session.getTransaction().commit();
       
session.close();
```
Šiame kodo gabaliuke pirmiausia yra sukuriama sesijos `Session` objektas iš konfiguracijos kuri būvo nusakyta ankstesniame skyriuje.
Tuomet norint pradėti darbą su `Enity` objektais ir jų įrašymu į duombazę reikia atidaryti transakcija. Transakcija yr atidaroma su
`session.getTransaction().begin();` ir baigiama su `session.getTransaction().commit();`
Taigi norint daryti update, insert ir delete komandas - jas būtina daryti dar atidarytos transakcijos.
`Select` tipo statement'am tai nebūtina, galima tiesiog naudoti funkcijas tokias kaip `session.find(class, id)`

Atsijungimas nuo duombazės visiškai yra tvarkomas kaip sesijos uždarymas: `session.close();`;
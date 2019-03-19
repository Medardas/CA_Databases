drop table if exists Contact;
drop table if exists Employee;
drop table if exists Company;
drop table if exists Address;

create table Address
(
  id     int primary key AUTO_INCREMENT,
  street varchar(40),
  city   varchar(30)
);
create table Company
(
  id      int primary key AUTO_INCREMENT,
  name    varchar(40),
  address int,
  CONSTRAINT fk_company_address FOREIGN KEY (address) REFERENCES Address (id)
);
create table Employee
(
  id       int primary key AUTO_INCREMENT,
  name     varchar(40),
  position varchar(30),
  company  int,
  address  int,
  CONSTRAINT fk_Employee_address FOREIGN KEY (address) REFERENCES Address (id),
  CONSTRAINT fk_Company FOREIGN KEY (company) REFERENCES Company (id)
);
create table Contact
(
  id           int primary key AUTO_INCREMENT,
  ref_id       int,
  contact_type varchar(30),
  value        varchar(40),
  CONSTRAINT fk_contact_Person FOREIGN KEY (ref_id) REFERENCES Employee (id)
);
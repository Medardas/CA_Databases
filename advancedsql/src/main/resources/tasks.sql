/*
Views
1. Create view to show person and how many hours have he/she worked.
2. Create View with check option to show name, position and hourly of an employee with hourly being more than 24
  2.1. insert any new value into this view
*/
/*1. solution*/
create view position_hourly as
select name, position, hourly
from employee
where hourly > 24 with check option;
/*1.1 solution */
insert into position_hourly(name, position, hourly)
VALUES ('some name', 'some position', 43);

/*
Trigger
1. Create trigger to insert hourly rate into new table with one column each time new employee is inserted
2. Create new table with one column of double type and insert value 0. Then create trigger to update that value every time new employee is inserted to arithmetically add hourly rate.
*/

/*1 solution*/
CREATE TRIGGER ins_hourly
  BEFORE INSERT
  ON employee
  for each row
  insert into hourly_spending
  values (NEW.hourly);
/*2 solution*/
CREATE TRIGGER sum_hourly_on_insert
  BEFORE INSERT
  ON employee
  for each row
  update hourly_spending
  set sum = sum + NEW.hourly;

/* Functions
1. Create query to show average hourly rate in euros for all employees rounded to one tenth.
2. Create function which takes integer and string to print "even + <given_string>" if integer is even and "odd + <given_string>" if integer is odd;
  https://dev.mysql.com/doc/refman/8.0/en/if.html
 */

-- 1. Solution
SELECT ROUND(AVG(e.hourly * c.rate), 1)
from employee e
       left join currency c on e.currency = c.name;

-- 2. Solution
CREATE FUNCTION oddOrEven(id INT(10), s VARCHAR(30))
  RETURNS CHAR(50) DETERMINISTIC
BEGIN
  DECLARE divisionType VARCHAR(20);
  IF id % 2 = 0 then
    SET divisionType = 'even ';
  else
    set divisionType = 'odd ';
  END IF;
  RETURN CONCAT(divisionType, s);
END;
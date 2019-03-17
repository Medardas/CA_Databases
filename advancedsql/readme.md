# Advanced SQL

Pavyzdžiuose bus naudojamas stalai kurie atrodo taip:
```
\------------------/
|     Person       |
--------------------
| ID | NAME  | AGE |
--------------------
| 1  | Jonas | 10  |
| 2  | Tomas | 53  |
| 3  | Egle  | 22  |
| 4  | Toma  | 33  |
| 5  | Ieva  | 22  |
/------------------\

\--------------------/
|     Salary       |
----------------------
| PERSON_ID |  PAY   |
----------------------
|     1     |  1000  |
|     2     |  2500  |
|     3     |     0  |
|     5     |  1234  |
/--------------------\

Salary(PERSON_ID) yra FOREIGN KEY lentelei Person(ID);
Person(ID) turi AUTO_INCREMENT savybę
```


### `VIEW`

`VIEW` yra tiesiog SQL užklausa išsaugota duombazėje kuriai duotas vardas. Kai `View` SQL užklausa yra sukurta ją vėliau galima panaudoti 
kada nori kreipiantis į ją per jai suteiktą vardą.

Sintaksė:
```sql
CREATE VIEW <any_view_name> AS SELECT <..>
```

Iš principo `View` sukurimas yra tiesiog raktažodžių`CREATE VIEW <any_view_name> AS` pridėjimas prieš `SELECT` užklausą.
Kuomet `View` sukurta, ant jos galima paleisti `SELECT` užklausas tokiu pačiu būdu kaip jos būtų paleistos ant duombazės lentelės.<br>
Taigi, tokia užklausa:
```sql
CREATE VIEW age_groups as SELECT age, COUNT(age) FROM person GROUP BY age;
```
Sukurtu `View` pavadinimu `age_groups` kurią dabar galima `SELECT`'inti:
```sql
SELECT * FROM age_groups
```
Tai grąžintų tą patį ką gražina nusakyta `SELECT` užklausa panaudota sukuriant `View` (netgi stulepių pavadinimai išliktų):
```
| age | COUNT(age) |
--------------------
| 10  |          1 |
| 22  |          2 |
| 33  |          1 |
| 53  |          1 |

```
`View` taip pat galima atnaujinti(`UPDATE`) ir į jas įvesti naujas reikšmes (`INSERT`). Tačiau tam reikia tenkinti kelias taisykles:

* `SELECT` užklausa negali turėti stulpelių su savybe `DISTINCT`.

* `SELECT` užklausa negali turėti funkcijų kurios dirba su keliais stulpeliais, tokios kaip `SUM()`, `MIN()`, `MAX()` ir t.t.

* `SELECT` užklausa negali turėti `ORDER BY` savybės.

* `FROM` dalis negali turėti kelių stalų (dirbant su `UNION` arba `JOIN`).

* `WHERE` negali turėti Sub-queries.

* Užklausa negali turėti savybės `GROUP BY` arba `HAVING` (`HAVING` tas pats kas `WHERE` kuris gali dirbti su funkcijomis).

* Stulpeliai kurie yra paskaičiuojami (pvz su `Trigger`'iais) negali būti atnaujinti su naujomis reikšmėmis.

* Visi `NOT NULL` stulpeliai turi būti įtraukti į `View`, kad INSERT užklausa veiktu.

Kai šios taisyklės patenkintos `INSERT` ir `UPDATE` užklausos veikia taip pat kaip su paprastu stalu. Nors šias užklausas leistumėt ant `View`, už širmos duombazė pasirūpina teisingai modifikuoti stalus kurie sudaro nurodyta `View`. Taigi, betkokie pakeitimai ant `View` taip pat matysis ant stalų iš kurių ji padaryta

Pridedant `WITH CHECK OPTION` savybę gale `CREATE VIEW` užklausos galima padayti, kad naujos `INSERT` ir `UPDATE` užklausos tenkintu logines taisykles nurodytas ``WHERE` dalyje.
Taigi sukuriant toki view: 
```sql
CREATE VIEW seniors as SELECT p.name, p.age from person p
where age > 50 WITH CHECK OPTION ;
```
Nauji `INSERT`'ai ar `UPDATE`'ai į `senios` neveiktų jei amžius mažiau nei 51

Atreipkit dėmesį, kad jei `WITH CHECK OPTION` nebūtu, `INSERT`'ai ar `UPDATE`'ai su amžium > 50 veiktų, tačiau kai `SELECT`'intumėt šį `seniors` `View` - jie nesimatytų, nes tiesiog `View` esančio `SELECT`'o taisyklės nėra tenkinamos.


Pagrindinė `View` funkcija yra supraprastinti sudėtingas, dažnai naudojamas užklausas ir apriboti bei apsaugoti duombazės naudotoją kad jis nesukurtų neteisingų užklausų.


### Indeksai

Indeksai yra specialios paieškos lentelės kurių tikslas yra pagreitinti užklausų veikimą panaudojant įrašų indeksavimą panašiu būdu kaip indeksas veikia knygos gale ieškoti reikiamos informacijos.
Indeksai pagreitina `SELECT` ir `WHERE` užklausų veikimą, tačiau įrašymas (`UPDATE` ir `INSERT`) būna šiek tiek lėtesnis.

Vieno stulpelio indekso sintakse atrodytų taip:
```sql  
CREATE INDEX <any_index_name> ON <table_name>(<column_name>);
```
Taip pat gali egzistuoti `UNIQUE` indeksai:
```sql
CREATE UNIQUE INDEX <index_name> on <table_name>(<column1>, <column2>);
```
Tokie indeksai iš principo veikia taip pat kaip pridėjus `UNIQUE` savybė prie stulpelio kuriant lentelė: jei neleistu duplikuotų reikšmių įrašymo.
Taip pat atreipkit dėmesi į **du stulpelius** šiame pavyzdyje. Indeksuoti galima du stuleplius, tai vadinamia *composite index*, tokie indeksai gerai veikia dažnai
ieškant įrašų lentelėje su užklausomis naudojančiomis `WHERE` savybę.

Jei prie stulpelio pavadinimo pridėsit raktažodį `DESC`:
```sql
CREATE INDEX <any_index_name> ON <table_name>(<column_name> DESC);
```
indeksas lyginimą pradės nuo didesnių reikšmių, tai reiškia didesnės reikšmės bus randamos greičiau. Default ASC.

### Triggers

Trigeriai yra operacijos egzistuojančios pačioje duombazėje kurios vykdomos atsitikus nustatytam veiksmui. Pavyzdžiui tai gali būti operacija kuri modifikuoja 
įrašą pagal nustatytą tvarką kiekvieną kartą kai naujas įrašas būna `INSERT`'inams į stalą

Pavyzdys:
```sql
  CREATE TRIGGER agecheck BEFORE INSERT ON person
    FOR EACH ROW
      IF NEW.age < 0
        THEN SET NEW.age = 0;
      END IF;
```
čia yra trigger'is kuris bus paleistas kiekvieną kartą kai nauja `INSERT` užklausa bus paleista ant `Person` stalo.
Šis trigeris patikrins ar amžius įrašomo asmens nėra mažiau nei 0 ir jei yra, amžius bus nustatomas į 0 ir tik tada įrašas nutiks.
Triggeriu sintaksė skiriasi nuo to ko norima pasiekti, bet iš esmės kiekvieno naujo trigerio užklausos pradžia bus `CREATE TRIGGER <any_trigger_name> <..>`
Daugiau apie sintaksę galima sužinoti čia: 
https://dev.mysql.com/doc/refman/5.7/en/trigger-syntax.html
Praktikoje triggerių reikėtų naudoti kuo mažiau, nes jie yra paleidžiami kiekvieną kartą kai kažkokia užklausa įvyksta ir taip lėtina duombazės darbą.
Taigi trigerius stengiamsi naudoti tik labai rizikingose situacijose, akd padidinti saugumą arba tada kai tikimasi, kad labai dažnai reikės apdirbti naujas užklausas.


### Temporary Tables

Kartais būna situacijų kai mes norim kažką apskaičiuoti per kelias užklausas ir mums reikia kažkur laikyti informacija iš pirminių užklausų,
kad jas patogiai paduoti į antrines užklausas. Tam į pagalbą ateina laikinosios lentelės. Laikinosios lentelės yra naudingos dirbant su tarpinėmis reikšmėmis (reikšmėmis kurios gautos atlikus kažkokią operaciją, bet jad dar reikės apdoroti, nes tai nėra ko reikia rezultate).<br>
Laikinosios lentelės visada bus ištrintos kai prisijungimo sesija baigsis ir `SHOW TABLES` užklausa jų nerodo.<br>
Sintaksė tokių lentelių yra tokia pat kaip `CREATE TABLE` sintaksė, tik šiuo atvėju reikia nurodyti, kad tai yra laikina lentelė tokiu būdu:
`CREATE TEMPORARY TABLE <table_name> (<column_definitions>)`

### Functions and procedures

MySQL turi nemažai funkcijų kurias galima naudoti užklausose. Funkcijos kurios yra jau duombazėje paruoštos naudojimui vadinamos `native` funkcijomis.
Apie kelias jau tikriausiai žinote, tokias kaip `SUM()`, `MAX()` ir taip toliau. 
Funkcijų sąrašą ir ką jos daro galite rasti čia:
https://www.w3schools.com/sql/sql_ref_mysql.asp
Taip pat egzistuoja procedūros. Tai yra operacijos kurios atlieką kažkokį veiksmą tačiau negali gražinti reikšmes stulpely, ne taip kaip funkcijos. Procedūros dar gali manipuliuoti pačia duombaze, tuo tarp funkcijos dirba su data.

`CREATE FUNCTION` sintksė yra ganėtinai sudėtinga ir ją galite rasti čia: https://dev.mysql.com/doc/refman/8.0/en/create-procedure.html
Pavizdys mūsų duombazėje galėtų atrodyti taip:

```sql
CREATE FUNCTION addHello (s CHAR(20))
  RETURNS CHAR(50) DETERMINISTIC
  RETURN CONCAT('Hello, ',s,'!');
```
Tuomet toks panaudojimas:
```sql
SELECT addHello(name) FROM Person;
```
Grąžintų:
```
\----------------/
| addHello(name) |
------------------
|  Hello, Jonas! |
|  Hello, Tomas! |
|   Hello, Egle! |
|   Hello, Toma! | 
|   Hello, Ieva! |
/----------------\
```

Tuo tarpu `CREATE PROCEDURE` pavyzdys atrodytų taip:
```sql
CREATE PROCEDURE setEntryNum(OUT param1 INT)
  BEGIN
    SELECT COUNT(name) INTO param1 FROM employee;
  END;
```
Čia yra procedūra kuriai yra paduodamas kintamasis ir ji į tą kintamajį įrašo nustatytą reikšme, šiuo atvejų `name` įrašų skaičių iš `Employee` lentelės
Patestuoti kaip veikia galima taip:
```sql
call setEntryNum(@a);  -- < naujas kintamas @a
select @a -- < pasirenkama kitamojo `a` reikšme kuri tiesiog grąžintų "SELECT COUNT(name) INTO param1 FROM employee" rezultatą 

\-----/
| @a  |
-------
|  7  |
/-----\


```


Yra įmanoma kurti savo kompiliuotas funkcijas ir įkelti į duombazę, tokios funkcijos vadinas UDF - user defined functions. Tačiau jos rašomos C arba C++ kalba ir tuo užsiima pagrinde tik duombazių ekspertai.
Daugiau apie tai jei įdomu: https://dev.mysql.com/doc/refman/8.0/en/adding-udf.html

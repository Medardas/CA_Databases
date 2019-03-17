# Databases course project

Kurso projektas yra egzaminavimo programa.
Funkciniai reikalavimai:
1. Programa turi galėti pateikti klausimus
    1.1 Prieš pateikiant klausimus programa turi paklausti kas sprendžia (paprastas vardo suvedimas yra užtektinai)
2. Kiekvienas klausimas turi turėti 3 atsakymo variantus.
3. Atsakymai turi būti priimti iš User Interface(UI) ir skaičiuojami prie bendro rezultato pagal nurodytas statistikas 6 punkte.
4. Programa turi turėti galimybę sukurti naujus klausimus ir klausimų variantus kiekvienam klausimui.
    * *Nebūtina funkcija*: kurti naujus klausimus turėtų būti galima tik suvedus slaptažodį.
5. Programa turi turėti galimybę modifikuoti esamus klausimus
    * *Nebūtina funkcija*: modifikuoti klausimus turėtų būti galima tik suvedus slaptažodį.

6. Turi būti galimybė pažiūrėti sekančias statistikas:
    
    a. Kiek kartų egzaminai buvo spręsti    
    b. Teisingų atsakymų skaičius.
      * totaliai
      * kiekviename klausimyne
      * vidurkis

    c. Kiek yra skirtingų atsakymo variantų pasirinkta (kiek buvo pasirinkta A, kiek B, kiek C)
    
      
7. Programa turi turėti galimybę būti išsijungta baigus darbą.

## Techniniai reikalavimai:
  1. Visa statistika turi būti išsaugota duombazėje:<br>
    1.1. Klausimynas<br>
    1.2. Kas sprendė<br>
    1.3. Atsakymų detalės
    
  2. Panaudoti visas CRUD operacijas
  3. Panaudoti kokį nors ORM framework'ą <br>
    3.1. Sukurti konfiguracinį failą<br>
    3.2. Sukurti `Entities`<br>
    3.3. Duombazės manipuliacija naudojant sukurtus `Entities`<br>
    Jei įmanoma pagal programos dizainą - tvarkingai įkomponuoti sudėtingiasnes užklausas: `JOIN`, `GROUP BY`, sub-queries, unions. 
  4. Jei egzistuoja slaptažodis manipuliuoti klausimams - jį duombazėje užšifruota.
  5. Programa turi panaudoti bent vieną native duombazės funkciją (`SUM()`, `MAX()` ir t.t.).
  6. Duombazė turi turėti bent viena logišką indeksą.
  7. Duombazė turi turėti bent vieną `View`.
  8. Programa turi turėti bent vieną trigger'į.
  
## Užduotys kartu su kursiniu darbu: 
  1. Paruošti duombazės UML diagramą.
  2. Paruošti duombazės inicializavimo užklausų rinkinį. Tai yra failas, ar keli, kurie susideda iš duombazės stalų ar kitų
   duombazėj reikalingų detalių sukurimo užklausų rinkinio.
  
  

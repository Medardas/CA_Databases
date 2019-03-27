# Mongo DB

Mongo Database yra multiplatforminė, non-relationl ir orentuota į dokumentus duombazės sistema. Šį `non-relational` 
(arba NoSQL) modelį naudoja daug skirtingų duombazių, tačiau Mongo DB dirba būtent su dokumentais kurie yra reprezentuojami JSON formatu.

### What is Non-relational database?

Non-relational duombazė nenaudoja tradicinio įrašų saryšio taisyklių kokias turi RDBMS tokie kaip MySQL, PostgreSQL ir t.t

Populiarėjančios ne reliacinės duombazės buvo sukurtos siekiant įveikti BigData poreikius. Big Data yra duomenys kurie auga pernelyg
sparčiai ir yra pernelyg įvairiai struktūrizuti, kad įprastos technologijos susitvarkytu.

Egzistuoja visokios NoSQL technologijos ir jos stipriai skirias tarpusavy, bet tokį modelį naudojančios duombazės tipiškai 
lagviau plečiamos (`scaling`) ir yra bendrai lankstesnės. Tai yra non-relationl duombazių evoliucijos (iš relational) pasekmė. Ši evoliucija remiasi tam tikromis savybėmis:
  * Data models: Skirtingai nuo relational, schemos NoSQL duombazėje yra daug lankstesnės. To rezultatas yra daug paprastesnė įrašų manipuliacija ir duombazės prisitaikymas priklausomai nuo sistemos reikalavimų.
  * Data structure: Non-relational duombazių dizainas leidžia tokiom duombazės laikyti nestrukturizuotus įrašus kurių nebutinai įmanoma gražiai sudėti į lenteles ir stulplelius. Nestrukturizuotos informacijos suvokimas yra svarbi savybė nes dauguma informacijos sugeneruota šiais laikais yra nestrukturizuota.

### Savybės bei Funkcijos

Minimalus duombazės paleidimo ir pirmo naudotojo sukūrimo procesas yra nusakytas kurso skaidrėse (prasideda maždaug ties 42 skaidre).
    
##### Kolekcijos (Collections)

Kaip minėta MongoDB duombazė savyje laiko dokumentus JSON formatu. Šių dokumentų rinkinys vadinasi kolekcija - `collection`. Kolekciją galima prilyginti lentelėms SQL tipo duombazėje, tuo tarpu JSON dokumentai būtų artimiausia analogija įrašams lentelėje.
Toks būdas tvarkyti informacijai yra bendra daugumai NoSQL duombazių.

Taigi, kad į MongoDB įdėti įraša pirmiausia reikia sukurti kolekciją:
```javascript
db.createCollection('firstCollection');
```

##### Dokumentai

MongoDB gali laikyti beveik betkokį JSON dokumentą betkokioje kolekcijoje, o jo įrašymas per konsolę atrodytų taip:

```javascript
db.firstCollection.insertOne({name:'Medardas',skill:'not MongoDB'});
```
Kai dokumentas yra įdedamas į duombazę, jam yra sugeneruojamas unikalus ID kurį nuo šiol matysit suradę dokumentą.

##### Find

Patikrinti įrašus šioje kolekcijoje galima taip:
```javascript
db.firstCollection.find();
```
Ši funkcija `find()` taip pat gali priimti argumentus, argumentai nusakytų salygas pagal kurias reikia rasti objektus.
Pavyzdžiui:
```javascript
db.firstCollection.find({name:'Medardas'});
```
Apie šia funkciją galima galvoti kaip apie `SELECT` užklausą SQL duombazėje kur kaikuriuos `WHERE` loginius lyginimus mes perduodam kaip argumentus.
Taigi šiuo atvėju funkcija ieškotų visų dokumentų kolekcijoje `firstCollection` ir gražintų visus kur yra laukas `name` su reikšme `Medardas`. Beja paieška yra case sensitive.

Laukų skaičius pagal kuriuos ieškoti neturi ribų, taigi iš principo jūsų paieškos objektas gali būti tiek didelis kiek reikia.

###### Projection
Antras funkcijos parametras vadinasi projekcija. Jis priima JSON objektą kuris nusako kuriuos laukus rezultate norime matyti plius objekto _id (nos _id grąžinimą irgi galima išjungti specifiškai tai nustačius).
Šio JSON objekto formatas yra `{<field_name>: <reikšmė>}` Reikšmės gali būti 1 arba 0 kas atitinkamai reiškia norime matyti ir nenorime matyti lauko

Norint nustatyti, kad negrąžintų _id fieldo ir grąžintų `skill` galima daryti taip: 
```javascript
db.firstCollection.find({name:"Medardas"},{_id: 0, skill:1});
```
Gražintų:
```json
{ "skill" : "not MongoDB" }
```
###### Regex

Find funkcija tiap pat supranta paieškos objektus su regex'ais. Taigi norint rasti visus dokumentus kur egzistuoja skill'as turintis "Mongo" savyje galyma rašyti taip:
```javascript
db.firstCollection.find({skill:/.*Mongo.*/})
```

###### Loginės operacijos

Loginės operacijos Mongo db yra nusakomos naudojant operatorius JSON objektuose kuriuos naudojat kaip paieškos parametrus.

Sintaksė:
```javascript
{ $<operator>: [ { <expression1> }, { <expression2> } , ... , { <expressionN> } ] }
```
Taigi surasti žmogu su skill'u "MongoDB" **arba** skill'u panašiu į HR galima taip:
```javascript
db.firstCollection.find({$or: [{skill:'MongoDB'},{skill:/.*HR.*/}]});
```
Logical operatoriai yra tokie:
  * and - turi tikti visi išsireiškimai
  * not - turi išsireiškimas
  * nor - turi netikti neivienas išsireiškimas
  * or - gali tikti betkuris išsireiškimas


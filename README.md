# New Year Gift: Java XML / XSD / Servlet coursework

A Java coursework project that models a New Year gift as a set of games and toys. Gift contents are read from an **XML** file **validated against an XSD schema**. The program computes the total weight, sorts items by price and finds items in a weight range. The same model is also shown on a web page through a **Servlet + JSP**.

## Features

- OOP model: abstract `Game` with `BoardGame` and `SoftToy` subclasses, plus a `Gift` aggregate
- **XSD validation** (`javax.xml.validation`) of `gift.xml` against `gift.xsd`
- **DOM parsing** (`DocumentBuilder`) of XML into objects, including typed child `<attribute>` elements
- Total weight, sorting with a `Comparator` (by price), range search with the Stream API (weight 0.4 to 0.9 kg)
- **Web version:** `GiftServlet` (`@WebServlet("/gift-report")`) prepares the data and forwards to `giftResult.jsp`
- `early-version/`: an earlier design with `Puzzle / Board / Action` nested subclasses, a factory method, `games.xml` and a `games.sql` table script

## Tech stack

Java 8+ · DOM / XSD (JAXP) · Java Servlet 3.1 · JSP · Maven (WAR packaging)

## Project structure

```
console/
  Main.java            console version: validate XML -> parse -> weight / sort / search
  gift.xml, gift.xsd
webapp/                Maven WAR project
  pom.xml
  src/main/java/com/gift/model/       Game, BoardGame, SoftToy, Gift
  src/main/java/com/gift/controller/  GiftServlet
  src/main/webapp/giftResult.jsp, index.jsp
early-version/
  Game.java, Gift.java, games.xml, games.sql   (switch expressions, needs Java 14+)
```

## How to run

**Console version** (JDK 8+):

```bash
cd console
javac Main.java
java Main
```

**Web version** (Maven + any Servlet 3.1 container, e.g. Tomcat 8.5/9):

```bash
cd webapp
mvn package            # -> target/gift.war
# deploy target/gift.war to Tomcat and open http://localhost:8080/gift/gift-report
```

> All data in this repository is randomly generated; brand names are anonymized.

Author: Dias Ayazbay (Java coursework).

## Кратко (RU)

Курсовая работа на Java о новогоднем подарке. Состав подарка читается из XML и проверяется по XSD-схеме.
Программа считает общий вес, сортирует игры по цене и ищет по диапазону веса.
Веб-версия показывает результат на странице через Servlet и JSP.

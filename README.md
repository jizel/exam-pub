# exam-cz.etyka.pub
Pub simulation api - REST, Spring, MySQL exam project

__Prerequisite:__ MySql DB running at localhost:3306 with database scheme _pub_db_ created (or change the application.properties file accordingly).

SpringBoot application
ompile with `mvn clean install`
Run with `mvn package && java -jar target/pub-1.0.jar` and access api at http://localhost:8080/

The following endpoints are accesible:

_/drink-menu_

_/users_

_/user/{id}_

_/buy_

_/summary/user_

_/summary/product_

_/summary/all_



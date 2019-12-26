# exam-cz.etyka.pub
Pub simulation api - REST, Spring, MySQL exam project

__Prerequisite:__ MySql DB running at localhost:3306 with database scheme _pub_db_ created (or change the application.properties file accordingly).

SpringBoot application - 
build with `mvn clean install`, run with `mvn package && java -jar target/pub-1.0.jar` and access api at http://localhost:8080/

The following endpoints are accesible:

_/drink-menu_

_/users_

_/user/{id}_

_/buy_ (POST)

_/summary/user_

_/summary/product_

_/summary/all_

Three users that can be used for authentication are preloaded in DB on application start:

__username:password__

alcoholic:1234

teenager:4321

bartender:admin

Bartender can access summaries but cannot buy a drink. Users (guests) can buy drinks, see menu and users. User can only buy a drink if he has money for it and is an adult (for alcoholic beverages).



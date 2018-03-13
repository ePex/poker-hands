# Poker Hands

This little tool will compare two given poker hands and tell you which one will win.

# Prerequisites 

* Java 8
* Working internet connection since the google material ui css and fonts will be fetched from the cdn 
and some maven dependencies will be downloaded 

# How to start

Make sure java8 home is on your path and then run the following commands
```bash
# compile the code
# if maven is not preinstalled on your machine you can use the mvnw or mvnw.cmd binaries
mvn clean package 
 
# run the application
java -jar target/poker-hands-0.0.1-SNAPSHOT.jar
```

# Todo

* Define interface(s) and implement service(s) 
* Separate header and footer into fragments, so that the google material ui css and js are only defined once
* Store google material ui, js and jQuery locally within the application so no more internet connection is required  
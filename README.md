# CORVID-19 API

API to check impact analysis of CORVID-19

## Built With

* 	[Maven](https://maven.apache.org/) - Dependency Management
* 	[JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit 
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications

## External Tools Used

* [Postman](https://www.getpostman.com/) - API Development Environment (Testing Docmentation)

## To-Do

- [✓] Logger (Console)
- [✓] RESTful Web Service (CRUD)
- [✓] Security (JWT Authentication)
- [✓] Docker
- [✓] JUNIT


## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.corvid.ninteen.impact.analysis` class from your IDE.

- Download the zip 
- Unzip the zip file (if you downloaded one)
- Open Command Prompt and Change directory (cd) to folder containing pom.xml
- Open Intellij 
   - File -> Open ->  Navigate to the folder where you unzipped the zip
   - Select the project
- Choose the Spring Boot Application file (search for @SpringBootApplication)
- Right Click on the file and Run as Java Application

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

### Security

```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Spring Boot Starter Security is for JWT token


### USERS URLs

|  URL |  Method | Remarks |
|----------|--------------|--------------|
|`http://localhost:8080/user/api/get_access_token/{any_user_name}` | GET | Header `Accept:application/json  ::: e.g replace {any_user_name} with Shahzeb`
|`http://localhost:8080/user/api/users/list` | GET | Header `Accept:application/json` Authorization Protected endpoint

### CORVID URLs

|  URL |  Method | Remarks |
|----------|--------------|--------------|
|`http://localhost:8080/corvid/cases/api/new`      	| GET | Header `Accept:application/json` Authorization Protected endpoint
|`http://localhost:8080/corvid/cases/api/new/country_wise`| GET | Header `Accept:application/json` Authorization Protected endpoint
|`http://localhost:8080/corvid/cases/api/new/{country_name}` | GET | Header `Accept:application/json   ::: e.g replace {country_name} with Pakistan` Authorization Protected endpoint
|`http://localhost:8080/corvid/cases/api/new/top/{number} ` | GET | Header `Accept:application/json ::: e.g replace {number} with 5`  Authorization Protected endpoint
|`http://localhost:8080/corvid/cases/api/all/date_and_country_wise} ` | GET | Header `Accept:application/json` RequestParam1 `country`  RequestParam2 `date` e.g country = Pakistan & date = 13-04-2020 (date format = DD-MM-YYYY)  Authorization Protected endpoint


## Documentation

* Corvid-19_Api.docs is also attached

## Files and Directories

The project (a.k.a. project directory) has a particular directory structure. A representative project is shown below:

```
.
├── Spring Elements
├── Maven dependencies
├── src
│   └── main
│       └── java
│           ├── com.corvid.ninteen.impact.analysis
│           ├── com.corvid.ninteen.impact.analysis.config
│           ├── com.corvid.ninteen.impact.analysis.controller
│           ├── com.corvid.ninteen.impact.analysis.filter
│           ├── com.corvid.ninteen.impact.analysis.mapper
│           ├── com.corvid.ninteen.impact.analysis.model
│           ├── com.corvid.ninteen.impact.analysis.service
│           └── com.corvid.ninteen.impact.analysis.util
├── src
│   └── main
│       └── resources
│           └── static
│           ├── templates
│           ├── application.properties
│           ├── time_series_covid19_confirmed_global.csv
├── src
│   └── test
│       └── java
│           ├── com.corvid.ninteen.impact.analysis
├── .gitignore
├── Corvid-19.iml
├── Dockerfile
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## packages

- `models` — to hold our entities;
- `services` — to hold our business logic;
- `security config` — security configuration;
- `controllers` — to listen to the client;
- `util` — to manage constants and static things;
- `mapper` — to manage generic logics e.g convertObjectToJson() etc;
- `filter` — security config filter;

- `resources/` - Contains all the static resources, templates and property files.
- `resources/application.properties` - It contains application-wide properties. Spring reads the properties defined in this file to configure your application. You can define server’s default port, server’s context path, file URLs etc, in this file.
- `resources/time_series_covid19_confirmed_global` - It is csv file for Corvid-19 data

- `test/` - contains unit and integration tests

- `pom.xml` - contains all the project dependencies
# Spring Boot IMDB Application

## Table of Contents
1. [Introduction](#introduction)
2. [Prerequisites](#prerequisites)
3. [Database Setup](#database-setup)
4. [Installation](#installation)
5. [Project Structure](#project-structure)
6. [Configuration](#configuration)
7. [Running the Application](#running-the-application)
8. [Testing the Endpoint](#testing-the-endpoint)


## Introduction
This Spring Boot application consumes the IMDB REST API to retrieve movie information based on a movie's title and returns the results via a REST endpoint. The application provides the following key functionalities:

Retrieve movie information (title, year, running time, lead actor) using the IMDB API.
Provide a REST endpoint that accepts a title query parameter and returns a JSON response with the movie information.
Persists movie information in a PostGreSQL database using Liquibase scripts.

## Prerequisites
- Java 11
- Maven
- PostGreSQL

## Database Setup
Before running the application, you need to set up the PostgreSQL database:

1. Connect to your PostgreSQL server and execute the following script to create the database and user:
    ```sql
    -- Creating Database
    CREATE DATABASE imdb;

    -- Create User
    CREATE USER your_username WITH PASSWORD 'your_password';

    -- User's Privileges
    GRANT ALL PRIVILEGES ON DATABASE imdb TO your_username;
    ```

## Installation
1. Unzip the file and build the project with the configuration that i provide.

2. Configure the database in \`src/main/resources/application.properties\`.

3. Run the application:
   ```sh
   mvn spring-boot:run
   ```

## Project Structure
````
src
└── main
   └── java
      └── gr
         └── grantthornton
            └── cf
               └── imdbapp
                  ├── controller
                  │   └── MovieController.java
                  ├── dto
                  │   └── MovieDTO.java
                  ├── mapper
                  │   └── Mapper
                  ├── model
                  │   └── Movie.java
                  ├── repository
                  │   └── MovieRepository.java
                  ├── service
                  │   ├── IMovieService.java
                  │   └── MovieServiceImpl.java
                  └── ImdbAppApplication.java
               └── resources
                  ├── application.properties
                  ├── db
                  └── changelog
                     └── db.changelog-master.xml
````
### src/main/java/com/grantthornton/cd/imdbapp/:

- ImdbAppApplication.java: Main Spring Boot application class.
- controller/MovieController.java: Handles incoming REST API requests.
- dto/MovieDTO.java: Data Transfer Object for movie information.
- mapper/Mapper.java: Converting a Movie to MovieDTO - separates data/entity representation.
- entity/Movie.java: JPA entity representing a movie.
- repository/MovieRepository.java: JPA repository for movie entity.
- service/IMovieService.java: Interface for movie service.
- service/MovieServiceImpl.java: Implementation of movie service.

### src/main/resources/:

- application.properties: Configuration file for Spring Boot and PostgreSQL.
- db/changelog/db.changelog-master.xml: Liquibase changelog for database schema.

## Configuration
### \`application.properties\`
````
spring.application.name=imdb-app
````
## IMDB API Configuration
````
imdb.api.url=https://imdb8.p.rapidapi.com/title/find?q= 
imdb.api.key=e25ce86aebmsh43574cbd727da86p17b382jsne8425c50d062
imdb.api.host=imdb8.p.rapidapi.com
````

## PostGreSQL Database Configuration
````
spring.datasource.url=jdbc:postgresql://localhost:5432/imdb?serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
````

# Liquibase Configuration
````
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml
````

## Running the Application
1. **Build the project**:
   ````sh
   mvn clean install
   ````

2. **Run the application**:
   ````sh
   mvn spring-boot:run
   ````

## Testing the Endpoint
Open your Postman to test the endpoint:

**GET Request**:
````
http://localhost:8080/title/find?title=Test Movie
````

**Example Response**:
````json
{
"title": "Test Movie",
"year": 2023,
"runningTimeInMinutes": 120,
"leadActor": "Test Actor"
}
````


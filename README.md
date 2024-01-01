# Filters

This is a back-end application built using Spring Boot and Java.
The application provides data to the front-end application 'FiltersClient' (available at https://github.com/Baldre/filters-client).

## Requirements

* Java 17
* Docker

## Setup instructions

### Database

The application uses a Postgres Docker container as a database.

Run in project root: ```docker-compose up```

### Application backend

Run in project root to start the application: ```./gradlew bootRun```

The application is hosted at `http://localhost:8080/`.
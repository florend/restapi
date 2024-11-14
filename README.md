# Demo Rest API

Simple REST API using Java Spring Boot
To be used in conjunction with my [frontend project](https://github.com/florend/angular-app) in Angular.
Or use a REST client like Postman.

## Requirements

- Java: `^17`
- Spring Boot `3.3.5`
- Docker

[Spring Initializr](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.5&packaging=jar&jvmVersion=17&groupId=com.example&artifactId=demo&name=demo&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.demo&dependencies=web,security,docker-compose,testcontainers,devtools,lombok,postgresql,data-jpa)

## API Documentation

Swagger 3
http://localhost:8080/swagger-ui/index.html

~~## Docker secrets~~

~~Add pg_password.txt at the root of the project containing the password of your postgres database.~~
Not supported by spring-boot-docker-compose

## Register a user

Use a REST Client to register a user.
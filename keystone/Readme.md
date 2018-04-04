# Keystone REST API
Kestone REST API Document

Date : 04/03/2018
# Design
  Kestone project has implemented the CRUD operations. 

# SRS (Software Requirement Specification)

- Java 1.8
- Maven 3.5.3
- IDE [ Spring Tool Suite]
- Spring Boot -1.5.10 RELEASE
- Spring Security
- Swagger 2
- Cloud Foundry - Pivotal
- In-Memory database [H2 DB]

#Project Structure
```
keystone
   ├───common-components
   │   └───src
   │       ├───main
   │       │   └───java
   │       │       └───com
   │       │           └───hcsc
   │       │               └───claim
   │       │                   └───simple
   │       │                       ├───config
   │       │                       └───exception
   │       └───test
   │           └───java
   │               └───com
   │                   └───hcsc
   │                       └───claim
   │                           └───simple
   └───product-service
       └───src
           ├───main
           │   ├───java
           │   │   └───com
           │   │       └───hcsc
           │   │           └───claim
           │   │               └───simple
           │   │                   ├───api
           │   │                   ├───exception
           │   │                   └───model
           │   │                       ├───entity
           │   │                       └───repository
           │   └───resources
           │       └───META-INF
           │           └───resources
           │               └───webjars
           │                   └───springfox-swagger-ui
           │                       ├───css
           │                       ├───fonts
           │                       ├───images
           │                       ├───lang
           │                       └───lib
           └───test-classes
               └───java
                   └───com
                       └───hcsc
                           └───claim
                               └───simple
                                   └───test

```


#Running the sample

1. Make sure that your server is running.
2. Change the Url to http://localhost:8999/swagger-ui.html.
3. You can start sending the Http requests and observe the output. 



## SimpleResource.java

A Rest Controller 'SimpleResource' exposes the service methods which are used to access our resources.
Get("/resources")- Returns resources available in the database.
Post("/resources")- To create new resources.
Put("/resources")- To update the resources.
Delete("/resources")- To delete all the resources.

To perform the operations on a specific resource we need to change the uri to ("/resources/{id}").
GET("/resources/{id}")- Returns a specific resource.
POST("/resources/{id}")- Method Not Allowed.
PUT("/resources/{id}")- To update a specific resource.
DELETE("/resources/{id}")- To delete a specific resource.


## SimpleResourceV2.java

This can be considered as the updated version in which we implement the validator interface so that no field is left empty and the values are must to create or update the resource. And a message with the particular Error code is returned.
And this version performs all the above crud operations.


##Swagger Dependency:-

Allows globally overriding response messages for different http methods.
Applications API can be represented in a single json file.
Following annotations attributes support description resolutions:

-@ApiParam: Adds additional meta-data for operation parameters.

-@ApiImplicitParam: Represents a single parameter in an API Operation.

-@ApiModelProperty: Adds and manipulates data of a model property.

-@ApiOperation: Describes an operation or typically a HTTP method against a specific path.



##JPA Dependecy:-

Spring Data JPA focuses on using JPA to store data in a relational database.
Its most compelling feature is the ability to create repository implementations automatically, at runtime, from a repository interface.


##Logback Encoder Dependency:-

Provides logback encoders, layouts, and appenders to log in JSON format.
Supports both regular LoggingEvents (logged through a Logger) and AccessEvents (logged via logback-access).
Originally written to support output in logstash's JSON format, but has evolved into a highly-configurable, general-purpose, JSON logging mechanism.
The structure of the JSON output, and the data it contains, is fully configurable.

##spring-boot-starters:-

Starters are a set of convenient dependency descriptors that you can include in your application. You get a one-stop-shop for all the Spring and related technology that you need without having to hunt through sample code and copy paste loads of dependency descriptors.

##Spring Cloud Services:-

Spring Cloud Services Starters are a curated set of dependencies for use with Spring Cloud Services in a Pivotal Cloud Foundry environment.

##Spring Cloud Slueth:-

Spring Cloud Sleuth implements a distributed tracing solution for Spring Cloud.
Add sleuth to the classpath of a Spring Boot application, and you can see the correlation data being collected in logs, as long as you are logging requests.

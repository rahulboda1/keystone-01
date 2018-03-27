#03/22/2018


# Keystone_Rest_API
Kestone Rest Api framework

# Design
  This project has the crud operations. A URL identifies a resource. GET, POST, PUT, DELETE to operate on the collections and elements.

Getting Started
Built With
SpringToolSuite
Maven

Running the sample
1. Make sure that server is running.
2. Change the Url to http://localhost:8999/swagger-ui.html.
3. You can start sending the Http requests and observe the output. 



# SimpleResource.java
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


# SimpleResourceV2.java
This can be considered as the updated version in which we implement the validator interface so that no field is left empty and the values are must to create or update the resource. And a message with the particular Error code is returned.
And this version performs all the above crud operations.



Authors

Acknowledgments

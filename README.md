# ReadingIsGood Java Case Application

## Using Tech/Lang/DB/Tools/Libs

1. Spring Boot with Java 11
2. MongoDB
3. OpenAPI 3
4. Lombok
5. Spring Security/Validation/Actuator
6. JWT
7. DozerBeanMapper
8. docker/docker-compose
9. Maven

## To run the application

1. Clone the repository.

if you have docker on your env
   
  2. Execute docker compose command -> `docker-compose up -d --build`
  3. MongoDB (Standalone/ReplicaSet) and ReadingIsGood java application will be ready few seconds.
  4. Check application and mongodb status in url -> http://localhost:8080/actuator/health

or

  2. If you dont have docker on your env, first you need to install manually the mongodb (Standalone/ReplicaSet - 4.4 version) then
  change to `mongodbConnectionString` attribute in `.src/main/resources/application-local.yml` file. After that, build
  the application with maven to run.


### Notes

- Mongodb will be working on `mongo://localhost:27017`
- MongoDB must be installed as ReplicaSet because of using `@Transactional` feature. 
  More information (https://docs.mongodb.com/manual/core/transactions and https://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/MongoTransactionManager.html)
- ReadingIsGood application have in-memory authentication mechanism.

        username: getir
        password: password

        bearer token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnZXRpciIsImV4cCI6MTYyODI0NTE3M30.zrBRw64lBdfD6A5m4CBSlZXMfA9ki0-0yj5YmHIrbwla3btoV-iSe_U92pzMoERm44YXIEakZDTbupSfc06RVw
        (90+ day valid token)


## To test the application

- `./ReadingIsGood.postman_collection.json` file includes example usage of application endpoints. You can import this
  file to Postman. Then check and try example requests.
- Or you can test via OpenUI -> http://localhost:8080/swagger-ui.html

## Some information for design

### Entities

#### Product

Entity which is created to store name, description, barcode, type, stock quantity and price.

#### Customer

Entity which is created to store name, surname and age.

#### Order

Entity which is created to store customer and shopping items information.

#### ActionLog

Entity which is created to store all database entities changes.
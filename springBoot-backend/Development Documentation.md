## Development Documentation

### 1. Introduction
The file explains all the classes, function and their workings in very detailed manner.

The project has been build on the concept of spring MVC.

* Controller  : BatteryController is responsible to accept and direct api requests.
* Model       : Battery Clas contains all the required fields which belongs to Battery like name, postcode and capacity.
* Service     : BatteryService is an interface which is implemented by all the classes (here BatteryServiceImpl)
                  that performs logical operation and updates database accordingly.
  
---
## 2. Controller  - Batter Controller 

### 2.i. Overview

This is a Spring Boot controller class for handling HTTP requests related to Battery entities. It includes two API endpoints for adding batteries and retrieving batteries by postcode range.
Dependencies

This class has dependencies on the following:

* Battery entity class
* BatteryService class for handling the business logic related to batteries
* SLF4J (LoggerFactory) for logging
* Spring Boot @Autowired annotation for dependency injection
* Spring Boot @RestController and @RequestMapping annotations for handling HTTP requests
* Spring Boot @PostMapping, @GetMapping, @RequestParam, and @RequestBody annotations for handling specific HTTP requests and parameters
---
### 2.ii. API Endpoints

1. POST /batteries/add

This endpoint is used for adding multiple batteries to the database. The endpoint expects an HTTP POST request with a JSON body containing a list of Battery objects. The controller will then pass this list to the BatteryService for saving the data to the database.

If there are any errors in the request or if any of the batteries already exist in the database, the controller will return an appropriate HTTP status code and message.

2. GET /batteries/

This endpoint is used for retrieving aggregated battery details based on a postcode range. The endpoint expects an HTTP GET request with startPostcode and endPostcode as query parameters. The controller will then pass these parameters to the BatteryService for retrieving the relevant battery details.

If there are any errors in the request or if there are no batteries that match the postcode range, the controller will return an appropriate HTTP status code and message.
Exceptions

This controller also handles several custom exceptions thrown by the BatteryService. These include:

* BatteryWithNameExistException: thrown if a battery with the same name already exists in the database
* InvalidRequestBodyException: thrown if the request body is not valid
* BatteriesSaveException: thrown if there is an error saving batteries to the database
* InvalidPostCodeRangeException: thrown if the postcode range is not valid

If any of these exceptions are thrown, the controller will return an appropriate HTTP status code and message.
Cross-Origin Resource Sharing (CORS)

This controller allows cross-origin resource sharing (CORS) for requests originating from http://localhost:3000. This is specified using the @CrossOrigin annotation.

---

### 2.iii. Logging

This controller uses SLF4J (LoggerFactory) for logging. 
It logs errors and messages related to the different API endpoints and exceptions thrown by the BatteryService.

---

## 3. Model  - Battery

### 3.i Overview 

This Java class is defining a Battery entity for a database schema in a Java Persistence API (JPA) project. 
It uses Lombok's @Data annotation to generate the getter and setter methods for all the fields.

The @Entity annotation is used to indicate that this class is a persistent entity and maps to a database table. 
The @Id annotation is used to specify the primary key of the entity, which is the name field in this case.

The Battery entity has three fields:

* name (String): Represents the name of the battery. It is marked with @Id, indicating that it is the primary key of the entity.
* postcode (String): Represents the postcode of the battery's location.
* capacity (Integer): Represents the capacity of the battery.

With the Battery entity class, you can perform database operations like insert, update, delete, and select records from the corresponding database table using JPA's EntityManager.

---
### 4. Service - BatteryServiceImpl

## 4.i Overview:
This is the implementation of the interface "BatteryService" that provides the business logic for battery operations. 
This service is responsible for saving and retrieving battery details from the database. 
It uses the BatteryRepository to perform database operations.

### 4.ii Annotations and Methods Used:

*  __@Service__ : This annotation indicates that the class is a Spring service component. It allows the class to be automatically discovered and registered as a bean in the Spring container during component scanning.

* __private static final Logger logger__ : This is a private static final variable of the Logger interface from the SLF4J logging framework. It is used to log messages in the service implementation for debugging and tracking purposes.

* __@Autowired__ : This annotation is used to inject a Spring bean dependency into the service implementation. It injects an instance of the BatteryRepository into the service.

* __public BatteryServiceImpl(BatteryRepository batteryRepository)__ : This is a constructor of the BatteryServiceImpl class. It initializes the BatteryRepository instance.

* __public boolean saveBatteries(List<Battery> batteries)__ : This method takes a list of Battery objects and performs the validation of the request data using the ServiceUtility.validateBatteryRequest method. If the data is valid, the method saves all the Battery objects using the batteryRepository.saveAll method.

* __@Override__ : This annotation indicates that the method is an overridden method from the BatteryService interface.

* __public Map<String, Object> retrieveBatteriesByPostCodeRange(String startPostcode, String endPostcode)__ : This method takes two String parameters startPostcode and endPostcode and validates the postcode range using the ServiceUtility.validatePostcodeRange method. If the postcode range is valid, the method retrieves a list of Battery objects using the batteryRepository.findByPostcodeBetween method. It then calculates the aggregated values of batteries using the ServiceUtility.calculateAggregatedValueOfBatteries method and returns the result as a Map<String, Object>.

* __try-catch blocks__ : These are the exception handling blocks that catch and handle exceptions that may occur during the execution of the business logic in the service implementation. They catch different exceptions and throw custom exceptions with specific error messages to inform the user of the error that occurred.

In summary, the BatteryServiceImpl class provides the implementation of the business logic for battery operations. It uses the BatteryRepository to perform database operations and the ServiceUtility to validate request data and calculate aggregated values of batteries. The class is a Spring service component and is responsible for saving and retrieving battery details from the database.

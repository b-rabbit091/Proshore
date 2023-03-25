### Test Scenarios

### 1. BatteryController Test

The BatteryControllerTest class contains unit tests for the BatteryController class. 
The tests cover the following methods with given scenarios:

 * __addBatteries_shouldReturnHttpStatusCreated()__  : This test checks if the addBatteries method of BatteryController returns a HTTP status of CREATED when the BatteryService returns true after successfully saving a list of batteries.

 * __addBatteries_shouldReturnHttpStatusBadRequestWithNullFields()__ : This test checks if the addBatteries method of BatteryController returns a HTTP status of BAD_REQUEST when the request body contains null values for name, postcode, and capacity.

 * __getBatteriesByPostcodeRange_shouldReturnHttpStatusOk()__ : This test checks if the getBatteriesByPostcodeRange method of BatteryController returns a HTTP status of OK when the BatteryService successfully retrieves a list of batteries based on a valid postcode range.

 * __getBatteriesByPostcodeRange_shouldReturnHttpStatusBadRequest()__ : This test checks if the getBatteriesByPostcodeRange method of BatteryController returns a HTTP status of BAD_REQUEST when the postcode range is invalid.

 * __getBatteriesByPostcodeRange_shouldReturnHttpStatusInternalServerError()__ : This test checks if the getBatteriesByPostcodeRange method of BatteryController returns a HTTP status of INTERNAL_SERVER_ERROR when an unexpected exception occurs during the retrieval of batteries.

 * __getBatteriesByPostcodeRange_shouldReturnHttpStatusOkWithEmptyResponseBody():__  This test checks if the getBatteriesByPostcodeRange method of BatteryController returns a HTTP status of OK when the BatteryService returns a null response.

 * __addBatteries_shouldReturnHttpStatusBadRequestWithNullRequestBody()__ : This test checks if the addBatteries method of BatteryController returns a HTTP status of BAD_REQUEST when the request body is null.

 * __addBatteries_shouldReturnHttpStatusBadRequestWithInvalidBatteryName()__ : This test checks if the addBatteries method of BatteryController returns a HTTP status of BAD_REQUEST when the request body contains a battery with a null name.

 * __addBatteries_shouldReturnHttpStatusBadRequestWithInvalidPostcode()__ : This test checks if the addBatteries method of BatteryController returns a HTTP status of BAD_REQUEST when the request body contains a battery with an invalid postcode.

 * __addBatteries_shouldReturnHttpStatusBadRequestWithInvalidCapacity()__ : This test checks if the addBatteries method of BatteryController returns a HTTP status of BAD_REQUEST when the request body contains a battery with an invalid capacity.

 * __addBatteries_shouldReturnHttpStatusBadRequest_whenBatteriesListIsEmpty()__ : This test checks if the addBatteries method of BatteryController returns a HTTP status of BAD_REQUEST when the request body contains an empty list of batteries.

 * __getBatteriesByPostcodeRange_shouldReturnHttpStatusBadRequest_whenPostcodeIsInvalid()__ : This test checks if the ServiceUtility method validatePostcodeRange correctly throws an IllegalArgumentException when the postcode range is invalid.

 * __getBatteriesByPostcodeRange_shouldReturnHttpStatusInternalServerError_whenBatteryServiceThrowsException()__ : This test checks if the getBatteriesByPostcodeRange method of BatteryController returns a HTTP status of INTERNAL_SERVER_ERROR when an unexpected exception occurs during the retrieval of batteries.

---

### 2. BatteryServiceImplTest

This is a test class named BatteryServiceImplTest in the com.example.demo.service package that tests the implementation of a BatteryService interface. 
Here's an overview of the class:

#### Annotations and Method used:

 * __import statements__ : This class imports various classes and static methods from different packages, including JUnit 5, Mockito, Battery, BatteryRepository, and several custom exception classes.

 * __@Mock annotation__ : This annotation is used to mock the BatteryRepository dependency of BatteryServiceImpl.

 * __@BeforeEach method__ : This method is executed before each test method is run. It initializes the mock objects using the MockitoAnnotations class and creates a new instance of BatteryServiceImpl using the mocked BatteryRepository instance.


  * __saveBatteries_shouldSaveBatteries__ : This test method tests whether the saveBatteries method correctly saves a list of batteries to the database.

  * __saveBatteries_shouldThrowInvalidRequestBodyException_whenRequestIsNull__ : This test method tests whether the saveBatteries method throws an InvalidRequestBodyException when the request body is null.

  * __saveBatteries_shouldThrowInvalidRequestBodyException_whenRequestIsEmpty__ : This test method tests whether the saveBatteries method throws an InvalidRequestBodyException when the request body is empty.

  * __saveBatteries_shouldThrowInvalidRequestBodyException_whenBatteryNameIsNull__ : This test method tests whether the saveBatteries method throws an InvalidRequestBodyException when a battery name is null.

  * __saveBatteries_shouldThrowInvalidRequestBodyException_whenPostcodeIsNull__ : This test method tests whether the saveBatteries method throws an InvalidRequestBodyException when a battery's postcode is null.

  * __saveBatteries_shouldThrowInvalidRequestBodyException_whenCapacityIsZero__ : This test method tests whether the saveBatteries method throws an InvalidRequestBodyException when a battery's capacity is zero.

  * __saveBatteries_shouldThrowInvalidRequestBodyException_whenPostcodeIsInvalid__ : This test method tests whether the saveBatteries method throws an InvalidRequestBodyException when a battery's postcode is invalid.

  * __saveBatteries_shouldThrowBatteriesSaveException_whenRepositoryThrowsException__ : This test method tests whether the saveBatteries method throws a BatteriesSaveException when the batteryRepository.saveAll method throws an exception.

  * __retrieveBatteriesByPostcodeRange_shouldReturnAggregatedBatteryDetails_whenPostcodeRangeIsValid__ : This test method tests whether the retrieveBatteriesByPostcodeRange method correctly retrieves and aggregates the battery details when the postcode range is valid.

  * __retrieveBatteriesByPostcodeRange_shouldThrowInvalidPostCodeRangeException_whenPostcodeRangeIsInvalid__ : This test method tests whether the retrieveBatteriesByPostcodeRange method throws an InvalidPostCodeRangeException when the postcode range is invalid.

  * __retrieveBatteriesByPostcodeRange_shouldThrowInvalidPostCodeRangeException_whenRepositoryThrowsException__ : This test method tests whether the retrieveBatteriesByPostcodeRange method throws an InvalidPostCodeRangeException when the batteryRepository.findByPostcodeBetween method throws an exception.

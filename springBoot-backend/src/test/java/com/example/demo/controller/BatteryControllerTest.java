package com.example.demo.controller;

import com.example.demo.entity.Battery;
import com.example.demo.exception.InvalidPostCodeRangeException;
import com.example.demo.exception.InvalidRequestBodyException;
import com.example.demo.service.BatteryService;
import com.example.demo.serviceUtility.ServiceUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BatteryControllerTest {

    @Mock
    private BatteryService batteryService;

    private BatteryController batteryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        batteryController = new BatteryController(batteryService);
    }

    @Test
    void addBatteries_shouldReturnHttpStatusCreated() {
        List<Battery> batteries = new ArrayList<>();
        Battery battery = new Battery();
        battery.setName("Test Battery");
        battery.setPostcode("1000");
        battery.setCapacity(100);
        batteries.add(battery);

        when(batteryService.saveBatteries(batteries)).thenReturn(true);

        ResponseEntity<String> responseEntity = batteryController.addBatteries(batteries);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @ParameterizedTest
    @CsvSource({
            "null, 1000, 100",
            "Test Battery, null, 100",
            "Test Battery, 1000, 0"
    })
    void addBatteries_shouldReturnHttpStatusBadRequestWithNullFields(String name, String postcode, Integer capacity) {
        List<Battery> batteries = new ArrayList<>();
        Battery battery = new Battery();
        battery.setName(name);
        battery.setPostcode(postcode);
        battery.setCapacity(capacity);
        batteries.add(battery);
        String message = "Failed to save batteries";
        if (name == null)
            message = "Battery name cannot be null";
        else if (postcode == null)
            message = "Postcode must be 4 characters long";
        else if (capacity < 0)
            message = "Capacity of battery must be greater than zero";

        when(batteryService.saveBatteries(batteries)).thenThrow(new InvalidRequestBodyException(message));
        ResponseEntity<String> responseEntity = batteryController.addBatteries(batteries);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @ParameterizedTest
    @CsvSource({
            "Failed to save batteries",
            "Unexpected exception occurred while saving batteries"
    })


    void getBatteriesByPostcodeRange_shouldReturnHttpStatusOk() throws InvalidPostCodeRangeException {
        String startPostcode = "1000";
        String endPostcode = "2000";

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("batteryNames", Arrays.asList("Battery1", "Battery2"));
        responseBody.put("totalCapacity", 1000);
        responseBody.put("avgCapacity", 500.0);

        when(batteryService.retrieveBatteriesByPostCodeRange(startPostcode, endPostcode)).thenReturn(responseBody);

        ResponseEntity<Map<String, Object>> responseEntity = batteryController.getBatteriesByPostcodeRange(startPostcode, endPostcode);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseBody, responseEntity.getBody());
    }


    @Test
    void getBatteriesByPostcodeRange_shouldReturnHttpStatusBadRequest() throws InvalidPostCodeRangeException {
        String startPostcode = "100";
        String endPostcode = "2000";

        when(batteryService.retrieveBatteriesByPostCodeRange(startPostcode, endPostcode)).thenThrow(new InvalidPostCodeRangeException("Postcode must be 4 characters long"));

        ResponseEntity<Map<String, Object>> responseEntity = batteryController.getBatteriesByPostcodeRange(startPostcode, endPostcode);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void getBatteriesByPostcodeRange_shouldReturnHttpStatusInternalServerError() throws InvalidPostCodeRangeException {
        String startPostcode = "1000";
        String endPostcode = "2000";

        when(batteryService.retrieveBatteriesByPostCodeRange(startPostcode, endPostcode)).thenThrow(new RuntimeException());

        ResponseEntity<Map<String, Object>> responseEntity = batteryController.getBatteriesByPostcodeRange(startPostcode, endPostcode);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void getBatteriesByPostcodeRange_shouldReturnHttpStatusOkWithEmptyResponseBody() throws InvalidPostCodeRangeException {
        String startPostcode = "1000";
        String endPostcode = "2000";

        when(batteryService.retrieveBatteriesByPostCodeRange(startPostcode, endPostcode)).thenReturn(null);

        ResponseEntity<Map<String, Object>> responseEntity = batteryController.getBatteriesByPostcodeRange(startPostcode, endPostcode);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void addBatteries_shouldReturnHttpStatusBadRequestWithNullRequestBody() {

        when(batteryService.saveBatteries(null)).thenThrow(new InvalidRequestBodyException("Request body is Empty"));
        ResponseEntity<String> responseEntity = batteryController.addBatteries(null);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }


    @Test
    void addBatteries_shouldReturnHttpStatusBadRequestWithInvalidBatteryName() {
        List<Battery> batteries = new ArrayList<>();
        Battery battery = new Battery();
        battery.setName(null);
        battery.setPostcode("1000");
        battery.setCapacity(100);
        batteries.add(battery);

        when(batteryService.saveBatteries(batteries)).thenThrow(new InvalidRequestBodyException("Battery name cannot be null"));
        ResponseEntity<String> responseEntity = batteryController.addBatteries(batteries);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void addBatteries_shouldReturnHttpStatusBadRequestWithInvalidPostcode() {
        List<Battery> batteries = new ArrayList<>();
        Battery battery = new Battery();
        battery.setName("Test Battery");
        battery.setPostcode("123");
        battery.setCapacity(100);
        batteries.add(battery);

        when(batteryService.saveBatteries(batteries)).thenThrow(new InvalidRequestBodyException("Postcode must be 4 characters long"));
        ResponseEntity<String> responseEntity = batteryController.addBatteries(batteries);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void addBatteries_shouldReturnHttpStatusBadRequestWithInvalidCapacity() {
        List<Battery> batteries = new ArrayList<>();
        Battery battery = new Battery();
        battery.setName("Test Battery");
        battery.setPostcode("1000");
        battery.setCapacity(-10);
        batteries.add(battery);

        when(batteryService.saveBatteries(batteries)).thenThrow(new InvalidRequestBodyException("Capacity of battery must be greater than zero"));
        ResponseEntity<String> responseEntity = batteryController.addBatteries(batteries);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void addBatteries_shouldReturnHttpStatusBadRequest_whenBatteriesListIsEmpty() {
        List<Battery> batteries = Collections.emptyList();

        when(batteryService.saveBatteries(batteries)).thenThrow(new InvalidRequestBodyException("Request body is Empty"));
        ResponseEntity<String> responseEntity = batteryController.addBatteries(batteries);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }


    @ParameterizedTest
    @CsvSource({
            ",'2000',Postcodes must be 4 characters long",
            "1000,'',Postcodes must be 4 characters long",
            "123,'2000',Postcodes must be 4 characters long",
            "1000,'123',Postcodes must be 4 characters long"
    })
    void getBatteriesByPostcodeRange_shouldReturnHttpStatusBadRequest_whenPostcodeIsInvalid(String startPostcode, String endPostcode, String expectedMessage) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ServiceUtility.validatePostcodeRange(startPostcode, endPostcode));
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void getBatteriesByPostcodeRange_shouldReturnHttpStatusInternalServerError_whenBatteryServiceThrowsException() throws InvalidPostCodeRangeException {
        String startPostcode = "1000";
        String endPostcode = "2000";

        when(batteryService.retrieveBatteriesByPostCodeRange(startPostcode, endPostcode)).thenThrow(new RuntimeException());

        ResponseEntity<Map<String, Object>> responseEntity = batteryController.getBatteriesByPostcodeRange(startPostcode, endPostcode);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

}

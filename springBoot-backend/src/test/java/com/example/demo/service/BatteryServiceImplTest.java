package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.entity.Battery;
import com.example.demo.exception.BatteriesSaveException;
import com.example.demo.exception.InvalidPostCodeRangeException;
import com.example.demo.exception.InvalidRequestBodyException;
import com.example.demo.repository.BatteryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class BatteryServiceImplTest {

    private BatteryService batteryService;

    @Mock
    private BatteryRepository batteryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        batteryService = new BatteryServiceImpl(batteryRepository);
    }

    @Test
    void saveBatteries_shouldSaveBatteries() {
        List<Battery> batteries = new ArrayList<>();
        Battery battery1 = new Battery();
        battery1.setName("Battery1");
        battery1.setPostcode("1000");
        battery1.setCapacity(100);
        batteries.add(battery1);

        when(batteryRepository.saveAll(batteries)).thenReturn(batteries);

        boolean result = batteryService.saveBatteries(batteries);

        assertTrue(result);
    }

    @Test
    void saveBatteries_shouldThrowInvalidRequestBodyException_whenRequestIsNull() {
        List<Battery> batteries = null;

        InvalidRequestBodyException exception = assertThrows(InvalidRequestBodyException.class, () -> batteryService.saveBatteries(batteries));

        assertEquals("Request body is empty", exception.getMessage());
    }

    @Test
    void saveBatteries_shouldThrowInvalidRequestBodyException_whenRequestIsEmpty() {
        List<Battery> batteries = new ArrayList<>();

        InvalidRequestBodyException exception = assertThrows(InvalidRequestBodyException.class, () -> batteryService.saveBatteries(batteries));

        assertEquals("Request body is empty", exception.getMessage());
    }

    @Test
    void saveBatteries_shouldThrowInvalidRequestBodyException_whenBatteryNameIsNull() {
        List<Battery> batteries = new ArrayList<>();
        Battery battery1 = new Battery();
        battery1.setName(null);
        battery1.setPostcode("1000");
        battery1.setCapacity(100);
        batteries.add(battery1);

        InvalidRequestBodyException exception = assertThrows(InvalidRequestBodyException.class, () -> batteryService.saveBatteries(batteries));

        assertEquals("Battery name cannot be empty", exception.getMessage());
    }

    @Test
    void saveBatteries_shouldThrowInvalidRequestBodyException_whenPostcodeIsNull() {
        List<Battery> batteries = new ArrayList<>();
        Battery battery1 = new Battery();
        battery1.setName("Battery1");
        battery1.setPostcode(null);
        battery1.setCapacity(100);
        batteries.add(battery1);

        InvalidRequestBodyException exception = assertThrows(InvalidRequestBodyException.class, () -> batteryService.saveBatteries(batteries));

        assertEquals("Postcode must be 4 characters long", exception.getMessage());
    }

    @Test
    void saveBatteries_shouldThrowInvalidRequestBodyException_whenCapacityIsZero() {
        List<Battery> batteries = new ArrayList<>();
        Battery battery = new Battery();
        battery.setName("Battery1");
        battery.setPostcode("1000");
        battery.setCapacity(0);
        batteries.add(battery);

        InvalidRequestBodyException exception = assertThrows(InvalidRequestBodyException.class, () -> {
            batteryService.saveBatteries(batteries);
        });

        assertEquals("Capacity of battery must be greater than zero", exception.getMessage());
    }

    @Test
    void saveBatteries_shouldThrowInvalidRequestBodyException_whenPostcodeIsInvalid() {
        List<Battery> batteries = new ArrayList<>();
        Battery battery = new Battery();
        battery.setName("Battery1");
        battery.setPostcode("1");
        battery.setCapacity(100);
        batteries.add(battery);

        InvalidRequestBodyException exception = assertThrows(InvalidRequestBodyException.class, () -> {
            batteryService.saveBatteries(batteries);
        });

        assertEquals("Postcode must be 4 characters long", exception.getMessage());
    }

    @Test
    void saveBatteries_shouldThrowBatteriesSaveException_whenRepositoryThrowsException() {
        List<Battery> batteries = new ArrayList<>();
        Battery battery = new Battery();
        battery.setName("Battery1");
        battery.setPostcode("1000");
        battery.setCapacity(100);
        batteries.add(battery);

        doThrow(new RuntimeException("Error occurred while saving batteries")).when(batteryRepository).saveAll(batteries);

        BatteriesSaveException exception = assertThrows(BatteriesSaveException.class, () -> {
            batteryService.saveBatteries(batteries);
        });

        assertEquals("Failed to save batteries: Error occurred while saving batteries", exception.getMessage());
    }

    @Test
    void retrieveBatteriesByPostcodeRange_shouldReturnAggregatedBatteryDetails_whenPostcodeRangeIsValid() {
        List<Battery> batteries = new ArrayList<>();
        Battery battery1 = new Battery();
        battery1.setName("Battery1");
        battery1.setPostcode("1000");
        battery1.setCapacity(100);
        batteries.add(battery1);

        Battery battery2 = new Battery();
        battery2.setName("Battery2");
        battery2.setPostcode("1500");
        battery2.setCapacity(200);
        batteries.add(battery2);

        when(batteryRepository.findByPostcodeBetween("1000", "1500")).thenReturn(batteries);

        Map<String, Object> batteryAggregatedDetails = batteryService.retrieveBatteriesByPostCodeRange("1000", "1500");

        assertNotNull(batteryAggregatedDetails);
        assertEquals(2, batteryAggregatedDetails.get("count"));
        assertEquals(300, batteryAggregatedDetails.get("totalCapacity"));
        assertEquals(150.0, batteryAggregatedDetails.get("averageCapacity"));
    }

    @Test
    void retrieveBatteriesByPostcodeRange_shouldThrowInvalidPostCodeRangeException_whenPostcodeRangeIsInvalid() {
        InvalidPostCodeRangeException exception = assertThrows(InvalidPostCodeRangeException.class, () -> {
            batteryService.retrieveBatteriesByPostCodeRange("12", "2000");
        });

        assertEquals("Invalid postcode range: Postcodes must be 4 characters long", exception.getMessage());
    }

    @Test
    void retrieveBatteriesByPostcodeRange_shouldThrowInvalidPostCodeRangeException_whenRepositoryThrowsException() {
        String startPostcode = "1000";
        String endPostcode = "2000";

        when(batteryRepository.findByPostcodeBetween(startPostcode, endPostcode))
                .thenThrow(new DataAccessException("Failed to retrieve data") {});

        assertThrows(InvalidPostCodeRangeException.class, () -> {
            batteryService.retrieveBatteriesByPostCodeRange(startPostcode, endPostcode);
        });
    }
}

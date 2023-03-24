package com.example.demo.controller;

import com.example.demo.entity.Battery;
import com.example.demo.exception.BatteriesSaveException;
import com.example.demo.exception.BatteryWithNameExistException;
import com.example.demo.exception.InvalidPostCodeRangeException;
import com.example.demo.exception.InvalidRequestBodyException;
import com.example.demo.service.BatteryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class BatteryController {

    private static final Logger logger = LoggerFactory.getLogger(BatteryController.class);

    @Autowired
    private BatteryService batteryService;

    public BatteryController(BatteryService batteryService) {
        this.batteryService = batteryService;
    }

    @PostMapping("/batteries/add")
    public ResponseEntity<String> addBatteries(@RequestBody List<Battery> batteries) {
        try {
            batteryService.saveBatteries(batteries);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BatteryWithNameExistException e) {
            logger.error("Battery with id already persisted in db :{}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InvalidRequestBodyException e) {
            logger.error("Request parameter is/are invalid: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (BatteriesSaveException e) {
            logger.error("Failed to save batteries: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/batteries/")
    public ResponseEntity<Map<String, Object>> getBatteriesByPostcodeRange(@RequestParam String startPostcode, @RequestParam String endPostcode) {
        try {
            Map<String, Object> batteryAggregatedDetails = batteryService.retrieveBatteriesByPostCodeRange(startPostcode, endPostcode);
            return ResponseEntity.ok(batteryAggregatedDetails);
        } catch (InvalidPostCodeRangeException e) {
            logger.error("Invalid postcode range: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Failed to retrieve batteries: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}


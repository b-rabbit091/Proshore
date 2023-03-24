package com.example.demo.serviceUtility;

import com.example.demo.entity.Battery;
import com.example.demo.exception.BatteryWithNameExistException;
import com.example.demo.exception.InvalidRequestBodyException;
import com.example.demo.repository.BatteryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceUtility {

    private ServiceUtility() {
    }

    public static Map<String, Object> calculateAggregatedValueOfBatteries(List<Battery> batteries) {
        Map<String, Object> response = new HashMap<>();

        List<String> batteryNames = batteries.stream()
                .map(Battery::getName)
                .sorted()
                .collect(Collectors.toList());

        int totalCapacity = batteries.stream()
                .mapToInt(Battery::getCapacity)
                .sum();

        double avgCapacity = batteries.stream()
                .mapToInt(Battery::getCapacity)
                .average()
                .orElse(0);
        response.put("count", batteries.size());
        response.put("batteryNames", batteryNames);
        response.put("totalCapacity", totalCapacity);
        response.put("averageCapacity", avgCapacity);
        return response;
    }

    public static void validatePostcodeRange(String startPostcode, String endPostcode) {
        if (startPostcode == null || startPostcode.length() != 4 ||
                endPostcode == null || endPostcode.length() != 4) {
            throw new IllegalArgumentException("Postcodes must be 4 characters long");
        }
    }

    public static void validateBatteryRequest(List<Battery> batteries, BatteryRepository batteryRepository) {
        if (batteries == null || batteries.isEmpty()) {
            throw new InvalidRequestBodyException("Request body is empty");
        }

        for (Battery battery : batteries) {
            if (battery.getName() == null || battery.getName().isBlank()) {
                throw new InvalidRequestBodyException("Battery name cannot be empty");
            }
            if (checkIfIdAlreadyExist(battery.getName(), batteryRepository)) {
                throw new BatteryWithNameExistException("Battery with name " + battery.getName() + " already exists");
            }
            if (battery.getPostcode() == null || battery.getPostcode().length() != 4) {
                throw new InvalidRequestBodyException("Postcode must be 4 characters long");
            }
            if (battery.getCapacity() < 1) {
                throw new InvalidRequestBodyException("Capacity of battery must be greater than zero");
            }
        }
    }

    private static boolean checkIfIdAlreadyExist(String name, BatteryRepository batteryRepository) {
        return batteryRepository.findById(name).isPresent();
    }
}

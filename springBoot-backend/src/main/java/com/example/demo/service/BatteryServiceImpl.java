package com.example.demo.service;

import com.example.demo.entity.Battery;
import com.example.demo.exception.BatteriesSaveException;
import com.example.demo.exception.BatteryWithNameExistException;
import com.example.demo.exception.InvalidPostCodeRangeException;
import com.example.demo.exception.InvalidRequestBodyException;
import com.example.demo.repository.BatteryRepository;
import com.example.demo.serviceUtility.ServiceUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BatteryServiceImpl implements BatteryService {

    private static final Logger logger = LoggerFactory.getLogger(BatteryServiceImpl.class);

    @Autowired
    private final BatteryRepository batteryRepository;

    public BatteryServiceImpl(BatteryRepository batteryRepository) {
        this.batteryRepository = batteryRepository;
    }

    @Override
    public boolean saveBatteries(List<Battery> batteries) {
        try {

            ServiceUtility.validateBatteryRequest(batteries, batteryRepository);
            batteryRepository.saveAll(batteries);
            logger.info("Saved {} batteries successfully", batteries.size());
            return true;
        }
        catch (BatteryWithNameExistException e) {
            throw new BatteryWithNameExistException(e.getMessage());
        } catch (InvalidRequestBodyException e) {
            throw new InvalidRequestBodyException(e.getMessage());
        } catch (Exception e) {
            throw new BatteriesSaveException("Failed to save batteries: " + e.getMessage());
        }
    }

    @Override
    public Map<String, Object> retrieveBatteriesByPostCodeRange(String startPostcode, String endPostcode) {
        try {
            ServiceUtility.validatePostcodeRange(startPostcode, endPostcode);
            List<Battery> batteries = batteryRepository.findByPostcodeBetween(startPostcode, endPostcode);
            logger.info("Retrieved {} batteries for postcode range {}-{}", batteries.size(), startPostcode, endPostcode);

            return ServiceUtility.calculateAggregatedValueOfBatteries(batteries);
        } catch (IllegalArgumentException e) {
            throw new InvalidPostCodeRangeException("Invalid postcode range: " + e.getMessage(), e);
        } catch (DataAccessException e) {
            throw new InvalidPostCodeRangeException("Failed to retrieve batteries: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new InvalidPostCodeRangeException("Unexpected error occurred while retrieving batteries: " + e.getMessage(), e);
        }
    }


}

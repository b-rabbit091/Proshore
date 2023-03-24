package com.example.demo.service;


import com.example.demo.entity.Battery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BatteryService {
    boolean saveBatteries(List<Battery> batteries);
    Map<String, Object> retrieveBatteriesByPostCodeRange(String startPostcode, String endPostcode);
}

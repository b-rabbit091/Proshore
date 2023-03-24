package com.example.demo.repository;

import com.example.demo.entity.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, String> {
    List<Battery> findByPostcodeBetween(String startPostcode, String endPostcode);
}

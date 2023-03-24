package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Battery {
    @Id
    private String name;
    private String postcode;
    private Integer capacity;

}


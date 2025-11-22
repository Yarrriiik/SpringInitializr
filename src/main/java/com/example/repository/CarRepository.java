package com.example.repository;

import com.example.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByBrandIgnoreCase(String brand);
    List<Car> findByYearGreaterThanEqual(Integer year);
    List<Car> findByPriceLessThanEqual(BigDecimal price);
    List<Car> findByColorIgnoreCase(String color);
}

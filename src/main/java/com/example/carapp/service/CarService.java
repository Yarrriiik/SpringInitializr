package com.example.carapp.service;

import com.example.carapp.domain.Car;
import com.example.carapp.repository.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarService {
    private final CarRepository repo;

    public Optional<Car> update(Long id, Car newData) {
        return repo.findById(id).map(car -> {
            // копируем ТОЛЬКО не-null значения — или просто все поля, если newData "полный"
            car.setBrand(newData.getBrand());
            car.setModel(newData.getModel());
            car.setColor(newData.getColor());
            car.setYear(newData.getYear());
            car.setPrice(newData.getPrice());
            return repo.save(car);
        });
    }

    public boolean delete(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }


    public CarService(CarRepository repo) { this.repo = repo; }

//    public Car create(String brand, String model, String color, Integer year, BigDecimal price) {
//        return repo.save(new Car(brand, model, color, year, price));
//    }

    public Car create(Car car) {
        return repo.save(car);
    }


    @Transactional(readOnly = true)
    public List<Car> findAll() { return repo.findAll(); }

    @Transactional(readOnly = true)
    public Optional<Car> findById(Long id) { return repo.findById(id); }

    public Car update(Long id, String brand, String model, String color, Integer year, BigDecimal price) {
        Car c = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Car not found: " + id));
        if (brand != null) c.setBrand(brand);
        if (model != null) c.setModel(model);
        if (color != null) c.setColor(color);
        if (year != null) c.setYear(year);
        if (price != null) c.setPrice(price);
        return repo.save(c);
    }

    @Transactional(readOnly = true)
    public List<Car> searchByBrand(String brand) { return repo.findByBrandIgnoreCase(brand); }

    @Transactional(readOnly = true)
    public List<Car> searchByYearMin(Integer year) { return repo.findByYearGreaterThanEqual(year); }

    @Transactional(readOnly = true)
    public List<Car> searchByMaxPrice(BigDecimal price) { return repo.findByPriceLessThanEqual(price); }

    @Transactional(readOnly = true)
    public List<Car> searchByColor(String color) { return repo.findByColorIgnoreCase(color); }
}

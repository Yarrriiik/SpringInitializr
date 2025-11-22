package com.example.service;

import com.example.domain.Car;
import com.example.repository.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarService {
    private final CarRepository repo;
    private final JmsNotificationService jms;

    public CarService(CarRepository repo, JmsNotificationService jms) {
        this.repo = repo;
        this.jms = jms;
    }

    // CRUD + JMS

    public Car create(Car car) {
        Car saved = repo.save(car);
        jms.sendAdmin("Car created: id=" + saved.getId());
        return saved;
    }

    public Optional<Car> update(Long id, Car newData) {
        return repo.findById(id).map(car -> {
            car.setBrand(newData.getBrand());
            car.setModel(newData.getModel());
            car.setColor(newData.getColor());
            car.setYear(newData.getYear());
            car.setPrice(newData.getPrice());
            Car updated = repo.save(car);
            jms.sendAdmin("Car updated: id=" + updated.getId());
            return updated;
        });
    }

    public Car update(Long id, String brand, String model,
                      String color, Integer year, BigDecimal price) {
        Car existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car not found: " + id));
        if (brand != null) existing.setBrand(brand);
        if (model != null) existing.setModel(model);
        if (color != null) existing.setColor(color);
        if (year != null) existing.setYear(year);
        if (price != null) existing.setPrice(price);

        Car updated = repo.save(existing);
        jms.sendAdmin("Car updated: id=" + updated.getId());
        return updated;
    }

    public boolean delete(Long id) {
        if (!repo.existsById(id)) {
            jms.sendAdmin("Car delete FAILED: id=" + id);
            return false;
        }
        repo.deleteById(id);
        jms.sendAdmin("Car deleted: id=" + id);
        return true;
    }

    @Transactional(readOnly = true)
    public List<Car> findAll() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Car> findById(Long id) {
        return repo.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Car> searchByBrand(String brand) {
        return repo.findByBrandIgnoreCase(brand);
    }

    @Transactional(readOnly = true)
    public List<Car> searchByYearMin(Integer year) {
        return repo.findByYearGreaterThanEqual(year);
    }

    @Transactional(readOnly = true)
    public List<Car> searchByMaxPrice(BigDecimal price) {
        return repo.findByPriceLessThanEqual(price);
    }

    @Transactional(readOnly = true)
    public List<Car> searchByColor(String color) {
        return repo.findByColorIgnoreCase(color);
    }
}

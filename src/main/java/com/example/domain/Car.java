package com.example.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Бренд обязателен")
    @Column(nullable = false)
    private String brand;

    @NotBlank(message = "Модель обязательна")
    @Column(nullable = false)
    private String model;

    @NotBlank(message = "Цвет обязателен")
    @Column(nullable = false)
    private String color;

    @Min(value = 1886, message = "Год не может быть меньше 1886")
    @Max(value = 2100, message = "Год слишком большой")
    @Column(nullable = false)
    private Integer year;

    @DecimalMin(value = "0.01", message = "Цена должна быть > 0")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    public Car() {}

    public Car(String brand, String model, String color, Integer year, BigDecimal price) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.year = year;
        this.price = price;
    }

    public Long getId() { return id; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", year=" + year +
                ", price=" + price +
                '}';
    }
}

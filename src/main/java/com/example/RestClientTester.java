package com.example;

import java.math.BigDecimal;
import com.example.domain.Car;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RestClientTester {
    public static void main(String[] args) {
        RestTemplate rest = new RestTemplate();
        String url = "http://localhost:8080/api/cars";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "admin123");
        headers.setContentType(MediaType.APPLICATION_JSON);

// GET список
        ResponseEntity<List<Car>> resp = rest.exchange(url, HttpMethod.GET, new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<Car>>() {});
        System.out.println("All cars: " + resp.getBody());

// POST
        Car newCar = new Car("Renault", "Logan", "blue", 2020, new BigDecimal("650000"));
        Car created = rest.exchange(url, HttpMethod.POST, new HttpEntity<>(newCar, headers), Car.class).getBody();
        System.out.println("Created car: " + created);

// GET by id
        Car found = rest.exchange(url + "/" + created.getId(), HttpMethod.GET, new HttpEntity<>(headers), Car.class).getBody();
        System.out.println("Found by id: " + found);

// PUT
        created.setColor("black");
        rest.exchange(url + "/" + created.getId(), HttpMethod.PUT, new HttpEntity<>(created, headers), Void.class);

// Проверка после PUT
        Car afterPut = rest.exchange(url + "/" + created.getId(), HttpMethod.GET, new HttpEntity<>(headers), Car.class).getBody();
        System.out.println("After PUT: " + afterPut);

//// DELETE
//        rest.exchange(url + "/" + created.getId(), HttpMethod.DELETE, new HttpEntity<>(headers), Void.class);
//
//// Проверка после DELETE — ожидаем 404
//        try {
//            rest.exchange(url + "/" + created.getId(), HttpMethod.GET, new HttpEntity<>(headers), Car.class);
//            System.out.println("Unexpected: resource still exists");
//        } catch (org.springframework.web.client.HttpClientErrorException.NotFound e) {
//            System.out.println("After DELETE: 404 Not Found (expected)");
//        }
//
    }
}

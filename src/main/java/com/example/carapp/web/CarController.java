package com.example.carapp.web;

import com.example.carapp.domain.Car;
import com.example.carapp.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {
    private final CarService service;

    public CarController(CarService service) { this.service = service; }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("cars", service.findAll());
        return "cars/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("car", new Car());
        return "cars/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("car") Car car, BindingResult br) {
        if (br.hasErrors()) return "cars/form";
        service.create(car.getBrand(), car.getModel(), car.getColor(), car.getYear(), car.getPrice());
        return "redirect:/cars";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Car car = service.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("car", car);
        return "cars/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @Valid @ModelAttribute("car") Car car,
                         BindingResult br) {
        if (br.hasErrors()) return "cars/edit";
        service.update(id, car.getBrand(), car.getModel(), car.getColor(), car.getYear(), car.getPrice());
        return "redirect:/cars";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/cars";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
                         @RequestParam(name = "brand", required = false) String brand,
                         Model model) {
        List<Car> result;
        if (maxPrice != null) result = service.searchByMaxPrice(maxPrice);
        else if (brand != null && !brand.isBlank()) result = service.searchByBrand(brand);
        else result = service.findAll();
        model.addAttribute("cars", result);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("brand", brand);
        return "cars/list";
    }
}

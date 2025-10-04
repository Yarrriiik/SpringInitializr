package com.example.carapp.console;

import com.example.carapp.domain.Car;
import com.example.carapp.service.CarService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleUI {
    private final CarService service;

    public ConsoleUI(CarService service) { this.service = service; }

    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            printMenu();
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> createCar(sc);
                    case "2" -> listAll();
                    case "3" -> updateCar(sc);
                    case "4" -> deleteCar(sc);
                    case "5" -> search(sc);
                    case "0" -> { System.out.println("Выход."); return; }
                    default -> System.out.println("Неизвестная команда");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\n==== Меню ====");
        System.out.println("1) Добавить автомобиль");
        System.out.println("2) Показать все");
        System.out.println("3) Редактировать по id");
        System.out.println("4) Удалить по id");
        System.out.println("5) Поиск");
        System.out.println("0) Выход");
        System.out.print("Выбор: ");
    }

    private void createCar(Scanner sc) {
        System.out.print("Бренд: "); String brand = sc.nextLine();
        System.out.print("Модель: "); String model = sc.nextLine();
        System.out.print("Цвет: "); String color = sc.nextLine();
        Integer year = readInt(sc, "Год (int): ");                 // заменили parseInt
        BigDecimal price = readDecimal(sc, "Цена (decimal): ");    // заменили new BigDecimal
        Car c = service.create(brand, model, color, year, price);
        System.out.println("Создано: " + c);
    }


    private void listAll() {
        List<Car> all = service.findAll();
        if (all.isEmpty()) System.out.println("Нет записей");
        else all.forEach(System.out::println);
    }

    private void updateCar(Scanner sc) {
        Long id = Long.parseLong(prompt(sc, "id: "));
        String brand = emptyAsNull(prompt(sc, "Новый бренд (пусто — не менять): "));
        String model = emptyAsNull(prompt(sc, "Новая модель (пусто — не менять): "));
        String color = emptyAsNull(prompt(sc, "Новый цвет (пусто — не менять): "));

        String yearStr = prompt(sc, "Новый год (пусто — не менять): ");
        Integer year = yearStr.isBlank() ? null : safeTryParseInt(yearStr);

        String priceStr = prompt(sc, "Новая цена (пусто — не менять): ");
        BigDecimal price = priceStr.isBlank() ? null : safeTryParseDecimal(priceStr);

        Car c = service.update(id, brand, model, color, year, price);
        System.out.println("Обновлено: " + c);
    }

    private String prompt(Scanner sc, String label) { System.out.print(label); return sc.nextLine(); }
    private Integer safeTryParseInt(String s) {
        try { return Integer.parseInt(s.trim()); } catch (NumberFormatException e) { System.out.println("Год не число, пропускаю изменение."); return null; }
    }
    private BigDecimal safeTryParseDecimal(String s) {
        try { return new BigDecimal(s.trim()); } catch (NumberFormatException e) { System.out.println("Цена не число, пропускаю изменение."); return null; }
    }


    private void deleteCar(Scanner sc) {
        System.out.print("id: "); Long id = Long.parseLong(sc.nextLine());
        service.delete(id);
        System.out.println("Удалено id=" + id);
    }

    private void search(Scanner sc) {
        System.out.println("1) По бренду  2) По мин. году  3) По макс. цене  4) По цвету");
        String s = sc.nextLine().trim();
        switch (s) {
            case "1" -> { System.out.print("Бренд: "); service.searchByBrand(sc.nextLine()).forEach(System.out::println); }
            case "2" -> { Integer y = readInt(sc, "Мин. год: "); service.searchByYearMin(y).forEach(System.out::println); }
            case "3" -> { BigDecimal p = readDecimal(sc, "Макс. цена: "); service.searchByMaxPrice(p).forEach(System.out::println); }
            case "4" -> { System.out.print("Цвет: "); service.searchByColor(sc.nextLine()).forEach(System.out::println); }
            default -> System.out.println("Нет такого варианта");
        }
    }

    private String emptyAsNull(String s) { return s == null || s.isBlank() ? null : s; }

    private Integer readInt(Scanner sc, String label) {
        while (true) {
            System.out.print(label);
            String s = sc.nextLine().trim();
            if (s.isEmpty()) { System.out.println("Пусто — попробуй ещё раз."); continue; }
            try { return Integer.parseInt(s); }
            catch (NumberFormatException e) { System.out.println("Не число — попробуй ещё раз."); }
        }
    }

    private BigDecimal readDecimal(Scanner sc, String label) {
        while (true) {
            System.out.print(label);
            String s = sc.nextLine().trim();
            if (s.isEmpty()) { System.out.println("Пусто — попробуй ещё раз."); continue; }
            try { return new BigDecimal(s); }
            catch (NumberFormatException e) { System.out.println("Не число — попробуй ещё раз."); }
        }
    }

}

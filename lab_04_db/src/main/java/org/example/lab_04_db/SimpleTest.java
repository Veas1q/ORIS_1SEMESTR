package org.example.lab_04_db;

import org.example.lab_04_db.repository.FlightRepository;
import java.time.LocalDate;

public class SimpleTest {
    public static void main(String[] args) {
        System.out.println("=== ПРОСТОЙ ТЕСТ ===");

        FlightRepository repo = new FlightRepository();

        // Тест 1: Рейсы из Казани
        var flights1 = repo.findFlights("KZN", "departure", LocalDate.of(2025, 10, 7));
        System.out.println("Рейсы из KZN: " + flights1.size());

        // Тест 2: Рейсы из Пулково
        var flights2 = repo.findFlights("LED", "departure", LocalDate.of(2025, 10, 7));
        System.out.println("Рейсы из LED: " + flights2.size());

        // Тест 3: Прилеты в Шереметьево
        var flights3 = repo.findFlights("SVO", "arrival", LocalDate.of(2025, 10, 7));
        System.out.println("Прилеты в SVO: " + flights3.size());
    }
}
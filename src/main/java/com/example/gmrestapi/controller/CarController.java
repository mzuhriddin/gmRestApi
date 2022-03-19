package com.example.gmrestapi.controller;


import com.example.gmrestapi.dto.ApiResponse;
import com.example.gmrestapi.entity.Car;
import com.example.gmrestapi.repository.CarRepository;
import com.example.gmrestapi.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/car")
@RequiredArgsConstructor
public class CarController {
    final CarRepository carRepository;
    final CarService carService;

    @GetMapping
    public HttpEntity<?> getAll() {
//        List<Car> all = carRepository.findAllByActiveTrue();
//        return ResponseEntity.ok().body(all);
        return null;
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable UUID id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        return ResponseEntity.status(optionalCar.isEmpty() ? 404 : 200).body(optionalCar.orElse(new Car()));
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody Car car) {
        Car add = carRepository.save(car);
        return ResponseEntity.ok().body(add);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, @RequestBody Car car) {
        ApiResponse edit = carService.edit(id, car);
        return ResponseEntity.status(edit.isSuccess() ? 200 : 409).body(edit);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id) {
        Optional<Car> byId = carRepository.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.status(404).body("NOT FOUND");
        }
        Car car = byId.get();
        car.setActive(false);
        carRepository.save(car);
        return ResponseEntity.ok().body("DELETED");
    }

//    @RequestParam boolean status
    @GetMapping("/change/{id}")
    public HttpEntity<?> changeActive(@PathVariable UUID id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            car.setActive(!car.isActive());
            carRepository.save(car);
        }
        return ResponseEntity.ok().body(optionalCar.orElseThrow(RuntimeException::new));
    }

    @GetMapping("/byAutoShop/{id}")
    public HttpEntity<?> getByAutoShop(@PathVariable Integer id) {
        return ResponseEntity.ok().body(carRepository.getAllByAutoShop(id));
    }
}

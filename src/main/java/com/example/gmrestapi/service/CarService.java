package com.example.gmrestapi.service;

import com.example.gmrestapi.dto.ApiResponse;
import com.example.gmrestapi.entity.Car;
import com.example.gmrestapi.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarService {
    final CarRepository carRepository;

    public ApiResponse edit(UUID id, Car car) {
        Optional<Car> byId = carRepository.findById(id);
        if (byId.isEmpty()) {
            return new ApiResponse("NOT FOUND", false);
        }
        Car edit = byId.get();
        edit.setModel(car.getModel());
        edit.setPrice(car.getPrice());
        edit.setYear(car.getYear());

        Car edited = carRepository.save(edit);
        return new ApiResponse("EDITED", true, edited);
    }
}

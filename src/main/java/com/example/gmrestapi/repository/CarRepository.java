package com.example.gmrestapi.repository;

import com.example.gmrestapi.entity.Car;
import com.example.gmrestapi.projection.CarProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
//    List<Car> findAllByActiveTrue();
    List<CarProjection> findAllByActiveTrue();

    @Query(value = "select * from car inner join auto_shop_cars ascs on car.id = ascs.cars_id where auto_shop_id=:id", nativeQuery = true)
    List<Car> getAllByAutoShop(Integer id);
}

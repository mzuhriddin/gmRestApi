package com.example.gmrestapi.repository;

import com.example.gmrestapi.entity.AutoShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutoShopRepository extends JpaRepository<AutoShop,Integer> {
    List<AutoShop> findAllByActiveTrue();
}

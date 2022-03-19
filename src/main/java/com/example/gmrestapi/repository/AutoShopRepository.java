package com.example.gmrestapi.repository;

import com.example.gmrestapi.entity.AutoShop;
import com.example.gmrestapi.projection.AutoShopProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AutoShopRepository extends JpaRepository<AutoShop,Integer> {
    List<AutoShopProjection> findAllByActiveTrue();

    List<AutoShop> findAllByGm_Id(UUID id);


}

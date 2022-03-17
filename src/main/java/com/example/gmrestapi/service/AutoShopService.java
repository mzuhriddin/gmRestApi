package com.example.gmrestapi.service;

import com.example.gmrestapi.dto.ApiResponse;
import com.example.gmrestapi.dto.AutoShopDto;
import com.example.gmrestapi.entity.Address;
import com.example.gmrestapi.entity.AutoShop;
import com.example.gmrestapi.entity.GM;
import com.example.gmrestapi.repository.AddressRepository;
import com.example.gmrestapi.repository.AutoShopRepository;
import com.example.gmrestapi.repository.CarRepository;
import com.example.gmrestapi.repository.GMRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutoShopService {
    final AddressRepository addressRepository;
    final AutoShopRepository autoShopRepository;
    final CarRepository carRepository;
    final GMRepository gmRepository;

    public ApiResponse add(AutoShopDto autoShopDto) {
        Address address = new Address();
        address.setCity(autoShopDto.getCity());
        address.setHome(autoShopDto.getHome());
        address.setStreet(autoShopDto.getStreet());
        Address save = addressRepository.save(address);

        AutoShop autoShop = new AutoShop();
        autoShop.setName(autoShopDto.getName());
        autoShop.setCars(carRepository.findAllById(autoShopDto.getCarIds()));
        autoShop.setGm(gmRepository.findById(autoShopDto.getGmId()).orElse(new GM()));
        autoShop.setAddress(save);
        autoShopRepository.save(autoShop);

        return new ApiResponse("ADDED", true);
    }

    public ApiResponse edit(Integer id, AutoShopDto autoShopDto) {
        Optional<AutoShop> optionalAutoShop = autoShopRepository.findById(id);
        if (optionalAutoShop.isEmpty()) {
            return new ApiResponse("AutoShop NOT FOUND", false);
        }

        AutoShop autoShop = optionalAutoShop.get();
        autoShop.setName(autoShopDto.getName());
        autoShop.setCars(carRepository.findAllById(autoShopDto.getCarIds()));
        autoShop.setGm(gmRepository.findById(autoShopDto.getGmId()).orElse(new GM()));

        Address address = autoShop.getAddress();
        address.setHome(autoShopDto.getHome());
        address.setCity(autoShopDto.getCity());
        address.setStreet(autoShopDto.getStreet());

        autoShop.setAddress(address);
        AutoShop edit = autoShopRepository.save(autoShop);
        return new ApiResponse("EDITED", true, edit);
    }
}

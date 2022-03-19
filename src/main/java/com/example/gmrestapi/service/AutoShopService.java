package com.example.gmrestapi.service;

import com.example.gmrestapi.dto.*;
import com.example.gmrestapi.entity.Address;
import com.example.gmrestapi.entity.AutoShop;
import com.example.gmrestapi.entity.Car;
import com.example.gmrestapi.entity.GM;
import com.example.gmrestapi.projection.AutoShopProjection;
import com.example.gmrestapi.repository.AddressRepository;
import com.example.gmrestapi.repository.AutoShopRepository;
import com.example.gmrestapi.repository.CarRepository;
import com.example.gmrestapi.repository.GMRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ApiResponse getAll() {
//        List<AutoShop> all = autoShopRepository.findAllByActiveTrue();
        List<AutoShopProjection> all = autoShopRepository.findAllByActiveTrue();
        return new ApiResponse("Mana royxat", true, all);
        //bu fordan tez ishlamaydi maqsad chiroyli kod yozish
//        List<RespAutoShop> collect = all.stream().map(this::autoShopToRespAutoShop).collect(Collectors.toList());
//        return new ApiResponse("Mana", true, collect);
    }

    public RespAutoShop autoShopToRespAutoShop(AutoShop autoShop) {
        return new RespAutoShop(
                autoShop.getName(),
                autoShop.getGm().getCorpName(),
                autoShop.getGm().getDirector(),
                autoShop.getCars().stream().map(this::carToCarDto).collect(Collectors.toList()),
                new AddressDto(autoShop.getAddress().getHome(),autoShop.getAddress().getStreet(), autoShop.getAddress().getCity())
        );
    }

    public CarDto carToCarDto(Car car){
        return new CarDto(
                car.getModel(),
                car.getYear(),
                car.getPrice()
        );
    }

}

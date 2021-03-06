package com.example.gmrestapi.service;

import com.example.gmrestapi.dto.ApiResponse;
import com.example.gmrestapi.dto.GMDto;
import com.example.gmrestapi.entity.Address;
import com.example.gmrestapi.entity.GM;
import com.example.gmrestapi.projection.GMProjection;
import com.example.gmrestapi.repository.AddressRepository;
import com.example.gmrestapi.repository.GMRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GMService {
    final AddressRepository addressRepository;
    final GMRepository gmRepository;
    public ApiResponse add(GMDto gmDto) {
        Address address = new Address();
        address.setCity(gmDto.getCity());
        address.setHome(gmDto.getHome());
        address.setStreet(gmDto.getStreet());
        Address save = addressRepository.save(address);

        GM gm = new GM();
        gm.setDirector(gmDto.getDirector());
        gm.setCorpName(gmDto.getCorpName());
        gm.setAddress(save);
        gmRepository.save(gm);

        return new ApiResponse("Added", true);
    }

    public ApiResponse edit(UUID id, GMDto gmDto){
        Optional<GM> optionalGM= gmRepository.findById(id);
        if (optionalGM.isEmpty()) {
            return new ApiResponse("GM NOT FOUND", false);
        }

        GM gm = optionalGM.get();
        gm.setDirector(gmDto.getDirector());
        gm.setCorpName(gmDto.getCorpName());

        Address address = gm.getAddress();
        address.setHome(gmDto.getHome());
        address.setCity(gmDto.getCity());
        address.setStreet(gmDto.getStreet());

        gm.setAddress(address);
        GM save = gmRepository.save(gm);
        return new ApiResponse("Edited", true, save);
    }

    public ApiResponse getAll() {
        List<GMProjection> all = gmRepository.findAllByActiveTrue();
        return new ApiResponse("Mana royxat", true, all);
        //bu fordan tez ishlamaydi maqsad chiroyli kod yozish
//        List<GMDto> collect = all.stream().map(this::gmToGmDTO).collect(Collectors.toList());
//        return new ApiResponse("Mana", true, collect);
    }

    //service metod orqali
    public GMDto gmToGmDTO(GM gm) {
        return new GMDto(
                gm.getCorpName(),
                gm.getDirector(),
                gm.getAddress().getHome(),
                gm.getAddress().getStreet(),
                gm.getAddress().getCity()
        );
    }
    //projection orqali olish ham mumkin
}

package com.example.gmrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RespAutoShop {
    private String name;
    private String gmName;
    private String gmDirector;
    private List<CarDto> cars;
    private AddressDto address;

}

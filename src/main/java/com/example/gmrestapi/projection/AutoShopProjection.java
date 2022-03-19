package com.example.gmrestapi.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface AutoShopProjection {
    String getName();

    @Value("#{target.gm.corpName}")
    String getGMName();

    @Value("#{target.gm.director}")
    String getGMDirector();

    List<CarProjection> getCars();

    @Value("#{target.address.city + ' '  + target.address.street + ' ' + target.address.home}")
    String getAddressName();
}

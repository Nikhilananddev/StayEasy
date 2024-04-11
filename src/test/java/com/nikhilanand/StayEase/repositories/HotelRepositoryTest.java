package com.nikhilanand.StayEase.repositories;

import com.nikhilanand.StayEase.exchanges.response.GetAllHotelResponse;
import com.nikhilanand.StayEase.model.HotelEntity;
import com.nikhilanand.StayEase.service.HotelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HotelRepositoryTest {

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    HotelService hotelService;


    @Test
    void addHotel() {
        HotelEntity hotelEntity = HotelEntity.builder()
                .hotelName("B")
                .description("B")
                .location("PATNA")
                .totalNumRooms(100)
                .numAvailableRooms(100)
                .build();

        hotelEntity = hotelRepository.save(hotelEntity);


        GetAllHotelResponse getAllHotelResponse = hotelService.getAllAvailableHotel();

        System.out.println(getAllHotelResponse.getHotels());
    }

}
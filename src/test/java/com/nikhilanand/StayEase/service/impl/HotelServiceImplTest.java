package com.nikhilanand.StayEase.service.impl;

import com.nikhilanand.StayEase.exchanges.request.AddHotelRequest;
import com.nikhilanand.StayEase.exchanges.response.HotelResponse;
import com.nikhilanand.StayEase.model.HotelEntity;
import com.nikhilanand.StayEase.repositories.HotelRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class HotelServiceImplTest {


    @Spy
    ModelMapper modelMapper;

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;


    @Test
    void createHotel() {

        HotelEntity hotelEntity = HotelEntity.builder()
                .hotelName("B")
                .description("B")
                .location("PATNA")
                .totalNumRooms(100)
                .numAvailableRooms(100)
                .build();

        AddHotelRequest addHotelRequest = AddHotelRequest.builder()
                .hotelName("B")
                .description("B")
                .location("PATNA")
                .totalNumRooms(100)
                .numAvailableRooms(100)
                .build();

        when(hotelRepository.save(any())).thenReturn(hotelEntity);


        HotelResponse hotelResponse = hotelService.createHotel(addHotelRequest);


        assertEquals("B", hotelResponse.getHotelDetails().getHotelName());
        assertEquals("B", hotelResponse.getHotelDetails().getDescription());
        assertEquals("PATNA", hotelResponse.getHotelDetails().getLocation());
        assertEquals(100, hotelResponse.getHotelDetails().getTotalNumRooms());
        assertEquals(100, hotelResponse.getHotelDetails().getNumAvailableRooms());
    }

    @Test
    void getHotelById() {
        HotelEntity hotelEntity = HotelEntity.builder()
                .hotelName("B")
                .description("B")
                .location("PATNA")
                .totalNumRooms(100)
                .numAvailableRooms(100)
                .build();


        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotelEntity));
        HotelResponse hotelResponse = hotelService.getHotelById(1L);


        assertEquals("B", hotelResponse.getHotelDetails().getHotelName());
        assertEquals("B", hotelResponse.getHotelDetails().getDescription());
        assertEquals("PATNA", hotelResponse.getHotelDetails().getLocation());
        assertEquals(100, hotelResponse.getHotelDetails().getTotalNumRooms());
        assertEquals(100, hotelResponse.getHotelDetails().getNumAvailableRooms());
    }
}
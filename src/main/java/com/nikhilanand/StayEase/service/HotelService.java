package com.nikhilanand.StayEase.service;

import com.nikhilanand.StayEase.exchanges.request.AddHotelRequest;
import com.nikhilanand.StayEase.exchanges.response.GetAllHotelResponse;
import com.nikhilanand.StayEase.exchanges.response.HotelResponse;

public interface HotelService {


    HotelResponse createHotel(AddHotelRequest addHotelRequest);


    HotelResponse getHotelById(Long hotelId);


    GetAllHotelResponse getAllAvailableHotel();


    HotelResponse updateHotel(Long hotelId, AddHotelRequest updateHotelRequest);

    void deleteHotelById(Long hotelId);
}

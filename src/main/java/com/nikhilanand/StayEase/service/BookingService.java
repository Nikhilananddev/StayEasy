package com.nikhilanand.StayEase.service;

import com.nikhilanand.StayEase.exchanges.request.AddBookingRequest;
import com.nikhilanand.StayEase.exchanges.response.BookingResponse;
import com.nikhilanand.StayEase.exchanges.response.GetAllBookingResponse;

public interface BookingService {


    BookingResponse addBooking(Long hotelId, AddBookingRequest addBookingRequest);

    GetAllBookingResponse getAllBooking();

    void deleteBooking(Long bookingId);


    BookingResponse cancelBooking(Long bookingId);


}

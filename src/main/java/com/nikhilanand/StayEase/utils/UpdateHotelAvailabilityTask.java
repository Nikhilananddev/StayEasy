package com.nikhilanand.StayEase.utils;

import com.nikhilanand.StayEase.model.BookingEntity;
import com.nikhilanand.StayEase.model.HotelEntity;
import com.nikhilanand.StayEase.repositories.BookingRepository;
import com.nikhilanand.StayEase.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
public class UpdateHotelAvailabilityTask {


    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Scheduled(fixedRate = 86400000) // Runs every 24 hours
    public void updateHotelAvailability() {
        LocalDate today = LocalDate.now();
        List<BookingEntity> bookings = bookingRepository.findByCheckOutDateLessThan(today);
        for (BookingEntity booking : bookings) {
            HotelEntity hotel = booking.getHotel();
            hotel.setNumAvailableRooms(hotel.getNumAvailableRooms() + 1);
            hotelRepository.save(hotel);
        }
    }


}

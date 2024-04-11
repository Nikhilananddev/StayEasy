package com.nikhilanand.StayEase.repositories;

import com.nikhilanand.StayEase.global.BookingStatus;
import com.nikhilanand.StayEase.model.BookingEntity;
import com.nikhilanand.StayEase.model.HotelEntity;
import com.nikhilanand.StayEase.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
class BookingRepositoryTest {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HotelRepository hotelRepository;


    @Test
    void addBooking() {
        UserEntity userEntity = new UserEntity();
        HotelEntity hotelEntity = new HotelEntity();

        Optional<HotelEntity> optionalHotel = hotelRepository.findById(1L);

        if (optionalHotel.isPresent())
            hotelEntity = optionalHotel.get();


        Optional<UserEntity> optionalUserEntity = userRepository.findById(1L);
        if (optionalUserEntity.isPresent())
            userEntity = optionalUserEntity.get();

        int totalNumberAvailableRooms = hotelEntity.getNumAvailableRooms();

        BookingEntity bookingEntity = BookingEntity.builder()
                .user(userEntity)
                .hotel(hotelEntity)
                .bookingDate(LocalDate.now())
                .checkInDate(LocalDate.of(2024, 4, 10))
                .checkInDate(LocalDate.of(2024, 4, 15))
                .status(BookingStatus.BOOKED).
                build();

        bookingEntity = bookingRepository.save(bookingEntity);


        hotelEntity.setTotalNumRooms(totalNumberAvailableRooms - 1);
        hotelRepository.save(hotelEntity);

        System.out.println(bookingEntity);
    }

}
package com.nikhilanand.StayEase.service.impl;

import com.nikhilanand.StayEase.exchanges.request.AddBookingRequest;
import com.nikhilanand.StayEase.exchanges.response.BookingResponse;
import com.nikhilanand.StayEase.global.BookingStatus;
import com.nikhilanand.StayEase.global.Role;
import com.nikhilanand.StayEase.model.BookingEntity;
import com.nikhilanand.StayEase.model.HotelEntity;
import com.nikhilanand.StayEase.model.User;
import com.nikhilanand.StayEase.model.UserEntity;
import com.nikhilanand.StayEase.repositories.BookingRepository;
import com.nikhilanand.StayEase.repositories.HotelRepository;
import com.nikhilanand.StayEase.repositories.UserRepository;
import com.nikhilanand.StayEase.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
class BookingServiceImplTest {

    @Mock
    AuthService authService;
    @Mock
    HotelRepository hotelRepository;
    @Mock
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Spy
    private ModelMapper mapper;
    @Mock
    private BookingRepository bookingRepository;
    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void addBooking() {


        HotelEntity hotelEntity = HotelEntity.builder()
                .id(1L)
                .hotelName("B")
                .description("B")
                .location("PATNA")
                .totalNumRooms(100)
                .numAvailableRooms(100)
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .role(Role.CUSTOMER)
                .password(passwordEncoder.encode("AASJ"))
                .build();


        BookingEntity bookingEntity = BookingEntity.builder()
                .user(userEntity)
                .hotel(hotelEntity)
                .bookingDate(LocalDate.now())
                .checkInDate(LocalDate.of(2024, 4, 10))
                .checkOutDate(LocalDate.of(2024, 4, 15))
                .status(BookingStatus.BOOKED).
                build();

        when(authService.getCurrentUserDetails()).thenReturn(new User(userEntity));
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotelEntity));

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        when(bookingRepository.save(any())).thenReturn(bookingEntity);


        AddBookingRequest addBookingRequest = AddBookingRequest.builder()
                .bookingDate(LocalDate.now())
                .bookingDate(LocalDate.now())
                .checkInDate(LocalDate.of(2024, 4, 10))
                .checkOutDate(LocalDate.of(2024, 4, 15))
                .status(BookingStatus.BOOKED)
                .build();

        BookingResponse bookingResponse = bookingService.addBooking(1L, addBookingRequest);

        assertEquals("B", bookingResponse.getBookingDetails().getHotel().getHotelName());
        assertEquals(LocalDate.now(), bookingResponse.getBookingDetails().getBookingDate());
        assertEquals(LocalDate.of(2024, 4, 10), bookingResponse.getBookingDetails().getCheckInDate());
        assertEquals(LocalDate.of(2024, 4, 15), bookingResponse.getBookingDetails().getCheckOutDate());
        assertEquals(BookingStatus.BOOKED, bookingResponse.getBookingDetails().getStatus());


    }

    @Test
    void cancelBooking() {

        HotelEntity hotelEntity = HotelEntity.builder()
                .id(1L)
                .hotelName("B")
                .description("B")
                .location("PATNA")
                .totalNumRooms(100)
                .numAvailableRooms(100)
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .role(Role.CUSTOMER)
                .password(passwordEncoder.encode("AASJ"))
                .build();
        BookingEntity bookingEntity = BookingEntity.builder()
                .user(userEntity)
                .hotel(hotelEntity)
                .bookingDate(LocalDate.now())
                .checkInDate(LocalDate.of(2024, 4, 10))
                .checkOutDate(LocalDate.of(2024, 4, 15))
                .status(BookingStatus.BOOKED).
                build();

        when(bookingRepository.findById(1L)).thenReturn(Optional.ofNullable(bookingEntity));

        bookingEntity.setStatus(BookingStatus.CANCELLED);
        when(bookingRepository.save(any())).thenReturn(bookingEntity);
        BookingResponse bookingResponse = bookingService.cancelBooking(1L);

        assertEquals("B", bookingResponse.getBookingDetails().getHotel().getHotelName());
        assertEquals(LocalDate.now(), bookingResponse.getBookingDetails().getBookingDate());
        assertEquals(LocalDate.of(2024, 4, 10), bookingResponse.getBookingDetails().getCheckInDate());
        assertEquals(LocalDate.of(2024, 4, 15), bookingResponse.getBookingDetails().getCheckOutDate());
        assertEquals(BookingStatus.CANCELLED, bookingResponse.getBookingDetails().getStatus());

    }
}
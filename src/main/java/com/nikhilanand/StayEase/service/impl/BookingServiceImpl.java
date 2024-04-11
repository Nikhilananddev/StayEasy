package com.nikhilanand.StayEase.service.impl;

import com.nikhilanand.StayEase.dto.BookingDTO;
import com.nikhilanand.StayEase.exceptions.booking.BookingNotFoundException;
import com.nikhilanand.StayEase.exceptions.hotel.HotelNotFoundException;
import com.nikhilanand.StayEase.exceptions.hotel.RoomNotAvailableException;
import com.nikhilanand.StayEase.exceptions.user.UserNotFoundException;
import com.nikhilanand.StayEase.exchanges.request.AddBookingRequest;
import com.nikhilanand.StayEase.exchanges.response.BookingResponse;
import com.nikhilanand.StayEase.exchanges.response.GetAllBookingResponse;
import com.nikhilanand.StayEase.global.BookingStatus;
import com.nikhilanand.StayEase.model.BookingEntity;
import com.nikhilanand.StayEase.model.HotelEntity;
import com.nikhilanand.StayEase.model.User;
import com.nikhilanand.StayEase.model.UserEntity;
import com.nikhilanand.StayEase.repositories.BookingRepository;
import com.nikhilanand.StayEase.repositories.HotelRepository;
import com.nikhilanand.StayEase.repositories.UserRepository;
import com.nikhilanand.StayEase.service.AuthService;
import com.nikhilanand.StayEase.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BookingServiceImpl implements BookingService {


    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BookingResponse addBooking(Long hotelId, AddBookingRequest addBookingRequest) {


        User userDetails = authService.getCurrentUserDetails();
        Long userId = userDetails.getId();


        UserEntity userEntity = new UserEntity();
        HotelEntity hotelEntity = new HotelEntity();

        Optional<HotelEntity> optionalHotel = hotelRepository.findById(hotelId);

        if (!optionalHotel.isPresent()) {
            try {
                throw new HotelNotFoundException("Hotel Not Found");
            } catch (HotelNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        hotelEntity = optionalHotel.get();


        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        if (!optionalUserEntity.isPresent()) {
            try {
                throw new UserNotFoundException("User Not Found");
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        userEntity = optionalUserEntity.get();

        int totalNumberAvailableRooms = hotelEntity.getNumAvailableRooms();

        if (totalNumberAvailableRooms <= 0)
            try {
                throw new RoomNotAvailableException("Room is Not Available");
            } catch (RoomNotAvailableException e) {
                throw new RuntimeException(e);
            }

        BookingEntity bookingEntity = BookingEntity.builder()
                .user(userEntity)
                .hotel(hotelEntity)
                .bookingDate(addBookingRequest.getBookingDate())
                .checkInDate(addBookingRequest.getCheckInDate())
                .checkOutDate(addBookingRequest.getCheckOutDate())
                .status(BookingStatus.BOOKED).
                build();

        bookingEntity = bookingRepository.save(bookingEntity);

        if (totalNumberAvailableRooms > 0) {
            hotelEntity.setNumAvailableRooms(totalNumberAvailableRooms - 1);

        }

        hotelRepository.save(hotelEntity);
        BookingResponse bookingResponse = BookingResponse.builder()
                .bookingDetails(modelMapper.map(bookingEntity, BookingDTO.class))
                .build();


        return bookingResponse;
    }

    @Override
    public GetAllBookingResponse getAllBooking() {
        List<BookingEntity> bookings = bookingRepository.findAll();


        List<BookingDTO> bookingDTOList = new ArrayList<>();
        for (BookingEntity booking : bookings) {
            BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);
            bookingDTOList.add(bookingDTO);
        }

        GetAllBookingResponse getAllBookingResponse = GetAllBookingResponse.builder()
                .bookings(bookingDTOList)
                .build();

        return getAllBookingResponse;
    }


    @Override
    public void deleteBooking(Long bookingId) {


        Optional<BookingEntity> optionalBookingEntity = bookingRepository.findById(bookingId);

        if (!optionalBookingEntity.isPresent()) {
            try {
                throw new BookingNotFoundException("Booking not found");
            } catch (BookingNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        bookingRepository.deleteById(bookingId);

    }

    @Override
    public BookingResponse cancelBooking(Long bookingId) {

        Optional<BookingEntity> optionalBookingEntity = bookingRepository.findById(bookingId);

        if (!optionalBookingEntity.isPresent()) {
            try {
                throw new BookingNotFoundException("Booking not found");
            } catch (BookingNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        BookingEntity bookingEntity = optionalBookingEntity.get();
        bookingEntity.setStatus(BookingStatus.CANCELLED);
        bookingEntity = bookingRepository.save(bookingEntity);

        BookingResponse bookingResponse = BookingResponse.builder()
                .bookingDetails(modelMapper.map(bookingEntity, BookingDTO.class))
                .build();

        return bookingResponse;
    }
}

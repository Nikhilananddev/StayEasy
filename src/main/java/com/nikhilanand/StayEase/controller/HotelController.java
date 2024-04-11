package com.nikhilanand.StayEase.controller;

import com.nikhilanand.StayEase.exchanges.request.AddBookingRequest;
import com.nikhilanand.StayEase.exchanges.request.AddHotelRequest;
import com.nikhilanand.StayEase.exchanges.response.BookingResponse;
import com.nikhilanand.StayEase.exchanges.response.GetAllHotelResponse;
import com.nikhilanand.StayEase.exchanges.response.HotelResponse;
import com.nikhilanand.StayEase.global.GlobalVariables;
import com.nikhilanand.StayEase.service.BookingService;
import com.nikhilanand.StayEase.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(GlobalVariables.STAY_EASE_API_ENDPOINT + "/hotels")
public class HotelController {


    @Autowired
    BookingService bookingService;
    @Autowired
    private HotelService hotelService;


    @GetMapping("/get/{hotelId}")
    @PreAuthorize("hasAuthority('HOTEL_MANAGER')")
    public ResponseEntity<HotelResponse> getHotel(@PathVariable Long hotelId) {
        HotelResponse hotelResponse = hotelService.getHotelById(hotelId);

        if (hotelResponse != null)
            return ResponseEntity.ok().body(hotelResponse);

        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/available")
    public ResponseEntity<GetAllHotelResponse> getAllAvailableHotels() {

        System.out.println(GlobalVariables.STAY_EASE_API_ENDPOINT);
        GetAllHotelResponse hotels = hotelService.getAllAvailableHotel();
        if (hotels != null)
            return ResponseEntity.ok().body(hotels);

        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HotelResponse> addHotel(@Valid @RequestBody AddHotelRequest addHotelRequest) {
        HotelResponse hotelDetails = hotelService.createHotel(addHotelRequest);
        if (hotelDetails != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(hotelDetails);

        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/update/{hotelId}")
    @PreAuthorize("hasAuthority('HOTEL_MANAGER')")
    public ResponseEntity<HotelResponse> updatehotel(@PathVariable Long hotelId, @Valid @RequestBody AddHotelRequest addHotelRequest) {
        HotelResponse hotelResponse = hotelService.updateHotel(hotelId, addHotelRequest);

        if (hotelResponse != null)
            return ResponseEntity.ok().body(hotelResponse);

        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/remove/{hotelId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteBook(@PathVariable Long hotelId) {
        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.ok().body("Hotel have been deleted");
    }


    @PostMapping("/{hotelId}/book")
    ResponseEntity<BookingResponse> bookHotel(@PathVariable("hotelId") Long hotelId, @Valid @RequestBody AddBookingRequest addBookingRequest) {

        BookingResponse bookingResponse = bookingService.addBooking(hotelId, addBookingRequest);
        if (bookingResponse != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponse);


        return ResponseEntity.badRequest().body(null);
    }


}

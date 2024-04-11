package com.nikhilanand.StayEase.controller;

import com.nikhilanand.StayEase.exchanges.response.BookingResponse;
import com.nikhilanand.StayEase.exchanges.response.GetAllBookingResponse;
import com.nikhilanand.StayEase.global.GlobalVariables;
import com.nikhilanand.StayEase.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(GlobalVariables.STAY_EASE_API_ENDPOINT + "/bookings")
public class BookingController {


    @Autowired
    BookingService bookingService;


    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('HOTEL_MANAGER')")
    public ResponseEntity<GetAllBookingResponse> getAllBooking() {


        GetAllBookingResponse bookings = bookingService.getAllBooking();
        if (bookings != null)
            return ResponseEntity.ok().body(bookings);

        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/cancel/{bookingId}")
    @PreAuthorize("hasAuthority('HOTEL_MANAGER')")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long bookingId) {
        BookingResponse bookingResponse = bookingService.cancelBooking(bookingId);

        if (bookingResponse != null)
            return ResponseEntity.ok().body(bookingResponse);

        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/remove/{bookingId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.ok().body("Booking have been deleted");
    }

}

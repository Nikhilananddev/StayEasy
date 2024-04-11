package com.nikhilanand.StayEase.exchanges.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nikhilanand.StayEase.global.BookingStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddBookingRequest {

    @JsonIgnoreProperties
    private Long id;

    @FutureOrPresent(message = "booking date must me future or present ")
    private LocalDate bookingDate;

    @FutureOrPresent(message = "checkInDate must me future or present ")
    private LocalDate checkInDate;

    @Future(message = "checkOutDate must me future date")
    private LocalDate checkOutDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}

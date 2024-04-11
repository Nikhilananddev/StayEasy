package com.nikhilanand.StayEase.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nikhilanand.StayEase.global.BookingStatus;
import com.nikhilanand.StayEase.model.HotelEntity;
import com.nikhilanand.StayEase.model.UserEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {

    @JsonIgnoreProperties
    private Long id;


    @NotNull
    private UserEntity user;

    @NotNull
    private HotelEntity hotel;

    @FutureOrPresent(message = "booking date must me future or present ")
    private LocalDate bookingDate;

    @FutureOrPresent(message = "checkInDate must me future or present ")
    private LocalDate checkInDate;

    @Future(message = "checkOutDate must me future date")
    private LocalDate checkOutDate;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BookingStatus status = BookingStatus.BOOKED;
}

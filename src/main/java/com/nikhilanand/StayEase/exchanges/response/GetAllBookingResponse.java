package com.nikhilanand.StayEase.exchanges.response;

import com.nikhilanand.StayEase.dto.BookingDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllBookingResponse {

    @Builder.Default
    List<BookingDTO> bookings = new ArrayList<>();

}

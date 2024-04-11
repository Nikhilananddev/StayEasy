package com.nikhilanand.StayEase.exchanges.response;

import com.nikhilanand.StayEase.dto.BookingDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {

    private BookingDTO bookingDetails;
}

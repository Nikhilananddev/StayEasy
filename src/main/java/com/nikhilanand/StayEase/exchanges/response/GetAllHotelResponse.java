package com.nikhilanand.StayEase.exchanges.response;

import com.nikhilanand.StayEase.dto.HotelDTO;
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
public class GetAllHotelResponse {

    @Builder.Default
    List<HotelDTO> hotels = new ArrayList<>();
}

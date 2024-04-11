package com.nikhilanand.StayEase.exchanges.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddHotelRequest {

    @JsonIgnoreProperties
    private Long id;

    @NotBlank(message = "Hotel name must not be blank")
    @Size(min = 1, max = 255, message = "Hotel name must be between {min} and {max} characters long")
    private String hotelName;

    @NotBlank(message = "Location must not be blank")
    @Size(min = 1, max = 255, message = "Location must be between {min} and {max} characters long")
    private String location;

    @NotBlank(message = "Description must not be blank")
    @Size(min = 1, max = 255, message = "Description must be between {min} and {max} characters long")
    private String description;

    @Positive(message = "Number of available rooms must be positive ")
    private int numAvailableRooms;

    @Positive(message = "Total number of rooms must be positive ")
    private int totalNumRooms;
}

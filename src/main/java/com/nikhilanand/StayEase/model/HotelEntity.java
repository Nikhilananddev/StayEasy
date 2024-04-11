package com.nikhilanand.StayEase.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "hotels")
public class HotelEntity {

//    Fields: Hotel Name, Location, Description, Number of Available Rooms

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hotel_name")
    private String hotelName;


    private String location;

    private String description;

    @Column(name = "num_of_available_rooms")
    private int numAvailableRooms;

    @Column(name = "total_num_of_rooms")
    private int totalNumRooms;

}

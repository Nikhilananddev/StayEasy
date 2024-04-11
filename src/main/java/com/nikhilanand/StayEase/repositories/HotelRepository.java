package com.nikhilanand.StayEase.repositories;

import com.nikhilanand.StayEase.model.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {


    @Query("SELECT h FROM HotelEntity h WHERE h.numAvailableRooms > 0")
    List<HotelEntity> findHotelsWithAvailableRooms();

}

package com.nikhilanand.StayEase.service.impl;

import com.nikhilanand.StayEase.dto.HotelDTO;
import com.nikhilanand.StayEase.exceptions.hotel.HotelNotFoundException;
import com.nikhilanand.StayEase.exchanges.request.AddHotelRequest;
import com.nikhilanand.StayEase.exchanges.response.GetAllHotelResponse;
import com.nikhilanand.StayEase.exchanges.response.HotelResponse;
import com.nikhilanand.StayEase.model.HotelEntity;
import com.nikhilanand.StayEase.repositories.HotelRepository;
import com.nikhilanand.StayEase.service.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public HotelResponse createHotel(AddHotelRequest addHotelRequest) {
        HotelEntity hotelEntity = HotelEntity.builder()
                .hotelName(addHotelRequest.getHotelName())
                .description(addHotelRequest.getDescription())
                .location(addHotelRequest.getLocation())
                .totalNumRooms(addHotelRequest.getTotalNumRooms())
                .numAvailableRooms(addHotelRequest.getNumAvailableRooms())
                .build();

        hotelEntity = hotelRepository.save(hotelEntity);

        HotelResponse hotelResponse = HotelResponse.builder()
                .hotelDetails(modelMapper.map(hotelEntity, HotelDTO.class))
                .build();
        System.out.println("createHotel " + hotelEntity);
        System.out.println("createHotel " + modelMapper);
        System.out.println("createHotel " + hotelResponse);
        return hotelResponse;

    }

    @Override
    public HotelResponse getHotelById(Long hotelId) {

        Optional<HotelEntity> hotel = hotelRepository.findById(hotelId);

        if (!hotel.isPresent()) {
            try {
                throw new HotelNotFoundException("Hotel not found");
            } catch (HotelNotFoundException e) {
                throw new RuntimeException(e);
            }

        }


        return HotelResponse.builder()
                .hotelDetails(modelMapper.map(hotel, HotelDTO.class))
                .build();
    }

    @Override
    public GetAllHotelResponse getAllAvailableHotel() {
        List<HotelEntity> hotels = hotelRepository.findHotelsWithAvailableRooms();


        List<HotelDTO> hotelDTOList = new ArrayList<>();
        for (HotelEntity hotel : hotels) {
            HotelDTO hotelDTO = modelMapper.map(hotel, HotelDTO.class);
            hotelDTOList.add(hotelDTO);
        }

        GetAllHotelResponse getAllHotelResponse = GetAllHotelResponse.builder()
                .hotels(hotelDTOList)
                .build();

        return getAllHotelResponse;
    }

    @Override
    public HotelResponse updateHotel(Long hotelId, AddHotelRequest updateHotelRequest) {
        Optional<HotelEntity> hotel = hotelRepository.findById(hotelId);

        if (!hotel.isPresent()) {
            try {
                throw new HotelNotFoundException("Hotel not found");
            } catch (HotelNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

        HotelEntity hotelEntity = hotel.get();

        hotelEntity.setHotelName(updateHotelRequest.getHotelName());
        hotelEntity.setDescription(updateHotelRequest.getDescription());
        hotelEntity.setLocation(updateHotelRequest.getLocation());
        hotelEntity.setTotalNumRooms(updateHotelRequest.getTotalNumRooms());
        hotelEntity.setNumAvailableRooms(updateHotelRequest.getNumAvailableRooms());


        hotelEntity = hotelRepository.save(hotelEntity);

        HotelResponse hotelResponse = HotelResponse.builder()
                .hotelDetails(modelMapper.map(hotelEntity, HotelDTO.class))
                .build();
        return hotelResponse;
    }

    @Override
    public void deleteHotelById(Long hotelId) {

        Optional<HotelEntity> optionalHotelEntity = hotelRepository.findById(hotelId);

        if (!optionalHotelEntity.isPresent()) {
            try {
                throw new HotelNotFoundException("Hotel not found");
            } catch (HotelNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        hotelRepository.deleteById(hotelId);

    }
}

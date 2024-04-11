package com.nikhilanand.StayEase.service.impl;

import com.nikhilanand.StayEase.dto.UserDTO;
import com.nikhilanand.StayEase.exceptions.user.UserEmailIdAlreadyExistsException;
import com.nikhilanand.StayEase.exceptions.user.UserNotFoundException;
import com.nikhilanand.StayEase.exchanges.request.AddUserRequest;
import com.nikhilanand.StayEase.exchanges.response.UserResponse;
import com.nikhilanand.StayEase.global.Role;
import com.nikhilanand.StayEase.model.UserEntity;
import com.nikhilanand.StayEase.repositories.UserRepository;
import com.nikhilanand.StayEase.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserResponse createUser(AddUserRequest request) {
        if (request.getRole() == null) {
            request.setRole(Role.CUSTOMER);
        }

        boolean isUserEmailIdExist = userRepository.existsByEmail(request.getEmail());

        if (isUserEmailIdExist) {
            try {
                throw new UserEmailIdAlreadyExistsException(request.getEmail() + " email id is already exist");
            } catch (UserEmailIdAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }

        UserEntity user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return UserResponse.builder()
                .userDetails(modelMapper.map(user, UserDTO.class))
                .build();

    }

    @Override
    public UserResponse getUserById(Long userId) {

        Optional<UserEntity> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            try {
                throw new UserNotFoundException("User not found");
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }

        }


        return UserResponse.builder()
                .userDetails(modelMapper.map(user, UserDTO.class))
                .build();
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            try {
                throw new UserNotFoundException("User not found");
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

        userRepository.deleteById(userId);

    }
}

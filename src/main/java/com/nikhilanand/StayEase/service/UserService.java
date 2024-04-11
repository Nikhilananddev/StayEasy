package com.nikhilanand.StayEase.service;

import com.nikhilanand.StayEase.exchanges.request.AddUserRequest;
import com.nikhilanand.StayEase.exchanges.response.UserResponse;

public interface UserService {

    UserResponse createUser(AddUserRequest addUserRequest);

    UserResponse getUserById(Long userId);

    void deleteUser(Long userId);
}


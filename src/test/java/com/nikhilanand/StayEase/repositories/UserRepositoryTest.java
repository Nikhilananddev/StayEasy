package com.nikhilanand.StayEase.repositories;

import com.nikhilanand.StayEase.global.Role;
import com.nikhilanand.StayEase.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Test
    void addUser() {

        UserEntity userEntity = UserEntity.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .role(Role.CUSTOMER)
                .password(passwordEncoder.encode("AASJ"))
                .build();

        userEntity = userRepository.save(userEntity);

        System.out.println(userEntity);
    }

}
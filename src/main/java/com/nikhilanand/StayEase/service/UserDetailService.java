package com.nikhilanand.StayEase.service;

import com.nikhilanand.StayEase.model.User;
import com.nikhilanand.StayEase.model.UserEntity;
import com.nikhilanand.StayEase.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(username);

        if (!optionalUserEntity.isPresent()) throw new UsernameNotFoundException("User not found ");


        return new User(optionalUserEntity.get());
    }
}

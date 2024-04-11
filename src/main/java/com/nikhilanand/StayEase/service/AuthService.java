package com.nikhilanand.StayEase.service;

import com.nikhilanand.StayEase.exceptions.user.UserEmailIdAlreadyExistsException;
import com.nikhilanand.StayEase.exchanges.request.AuthRequest;
import com.nikhilanand.StayEase.exchanges.request.RegisterRequest;
import com.nikhilanand.StayEase.exchanges.response.AuthResponse;
import com.nikhilanand.StayEase.global.Role;
import com.nikhilanand.StayEase.model.User;
import com.nikhilanand.StayEase.model.UserEntity;
import com.nikhilanand.StayEase.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTService jwtService;


    @Autowired
    AuthenticationManager authenticationManager;

    public AuthResponse login(AuthRequest request) {


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        Optional<UserEntity> userEntity = userRepository.findByEmail(request.getEmail());
        if (!userEntity.isPresent())
            throw new UsernameNotFoundException("user not found");

        String jwtToken = jwtService.generateToken(new User(userEntity.get()));
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {

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

        String jwtToken = jwtService.generateToken(new User(user));
        userRepository.save(user);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();

    }

    public User getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }
}

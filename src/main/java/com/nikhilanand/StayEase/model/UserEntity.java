package com.nikhilanand.StayEase.model;

//Users must be able to register by providing their email address and, password
//        The password must be encrypted and stored using BCrypt
//        Fields: Email, Password, First Name, Last Name, Role
//        The Role must be defaulted to “Customer” if it is not specified
//        A JWT token must be generated upon successful registration or login


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nikhilanand.StayEase.global.Role;
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
@Table(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;


    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.CUSTOMER;


}

// one to many user and booking

//one to many hotel to rooms

// one to many hotel to booking
package com.nikhilanand.StayEase.exchanges.response;

import com.nikhilanand.StayEase.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private UserDTO userDetails;

}

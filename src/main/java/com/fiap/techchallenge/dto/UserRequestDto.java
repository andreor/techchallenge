package com.fiap.techchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private Long userId;
    private String name;
    private String email;
    private String login;
    private String password;
    private String address;


}

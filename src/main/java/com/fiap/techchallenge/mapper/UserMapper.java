package com.fiap.techchallenge.mapper;

import com.fiap.techchallenge.dto.UserResponseDto;
import com.fiap.techchallenge.dto.UserRequestDto;
import com.fiap.techchallenge.repository.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

     UserRequestDto toDto(User user);
     UserResponseDto toResponseDto(User user);
     User  fromDto(UserRequestDto dto);

}

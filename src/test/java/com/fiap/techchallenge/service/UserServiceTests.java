package com.fiap.techchallenge.service;


import com.fiap.techchallenge.dto.UserChangePwdRequestDto;
import com.fiap.techchallenge.dto.UserRequestDto;
import com.fiap.techchallenge.dto.UserResponseDto;
import com.fiap.techchallenge.repository.UserRepository;
import com.fiap.techchallenge.repository.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void create_ReturnsUserResponseDto_WhenUserIsValid() {
        UserRequestDto userRequest = new UserRequestDto();
        User userEntity = new User();
        UserResponseDto userResponse = new UserResponseDto();

        when(modelMapper.map(userRequest, User.class)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserResponseDto.class)).thenReturn(userResponse);

        UserResponseDto result = userService.create(userRequest);

        assertNotNull(result);
        verify(userRepository).save(userEntity);
    }

    @Test
    void update_ReturnsUserResponseDto_WhenUserExists() {
        UserRequestDto userRequest = new UserRequestDto();
        userRequest.setUserId(1L);
        User userEntity = new User();
        UserResponseDto userResponse = new UserResponseDto();

        when(userRepository.findById(userRequest.getUserId())).thenReturn(Optional.of(userEntity));
        when(modelMapper.map(userRequest, User.class)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserResponseDto.class)).thenReturn(userResponse);

        UserResponseDto result = userService.update(userRequest);

        assertNotNull(result);
        verify(userRepository).save(userEntity);
    }

    @Test
    void update_ThrowsEntityNotFoundException_WhenUserDoesNotExist() {
        UserRequestDto userRequest = new UserRequestDto();
        userRequest.setUserId(1L);

        when(userRepository.findById(userRequest.getUserId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.update(userRequest));
        verify(userRepository, never()).save(any());
    }

    @Test
    void updatePwd_ReturnsUserResponseDto_WhenPasswordIsUpdated() {
        UserChangePwdRequestDto userChangePwdRequest = new UserChangePwdRequestDto();
        userChangePwdRequest.setUserId(1L);
        User userEntity = new User();
        UserResponseDto userResponse = new UserResponseDto();

        when(userRepository.findById(userChangePwdRequest.getUserId())).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(modelMapper.map(userEntity, UserResponseDto.class)).thenReturn(userResponse);

        UserResponseDto result = userService.updatePwd(userChangePwdRequest);

        assertNotNull(result);
        verify(userRepository).save(userEntity);
    }

    @Test
    void updatePwd_ThrowsEntityNotFoundException_WhenUserDoesNotExist() {
        UserChangePwdRequestDto userChangePwdRequest = new UserChangePwdRequestDto();
        userChangePwdRequest.setUserId(1L);

        when(userRepository.findById(userChangePwdRequest.getUserId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.updatePwd(userChangePwdRequest));
        verify(userRepository, never()).save(any());
    }

    @Test
    void delete_DeletesUser_WhenUserExists() {
        Long userId = 1L;
        User userEntity = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        userService.delete(userId);

        verify(userRepository).delete(userEntity);
    }

    @Test
    void delete_ThrowsEntityNotFoundException_WhenUserDoesNotExist() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.delete(userId));
        verify(userRepository, never()).delete(any());
    }
}
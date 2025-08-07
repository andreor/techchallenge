package com.fiap.techchallenge.controller;

import com.fiap.techchallenge.dto.UserChangePwdRequestDto;
import com.fiap.techchallenge.dto.UserRequestDto;
import com.fiap.techchallenge.dto.UserResponseDto;
import com.fiap.techchallenge.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void create_ReturnsCreatedResponse_WhenUserIsValid() {
        UserRequestDto userRequest = new UserRequestDto();
        UserResponseDto userResponse = new UserResponseDto();
        userResponse.setUserId(1L);

        when(userService.create(userRequest)).thenReturn(userResponse);

        ResponseEntity<UserResponseDto> response = userController.create(userRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/user/1", response.getHeaders().getLocation().toString());
        assertEquals(userResponse, response.getBody());
    }

    @Test
    void update_ReturnsOkResponse_WhenUserExists() {
        UserRequestDto userRequest = new UserRequestDto();
        UserResponseDto userResponse = new UserResponseDto();

        when(userService.update(userRequest)).thenReturn(userResponse);

        ResponseEntity<UserResponseDto> response = userController.update(userRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponse, response.getBody());
    }

    @Test
    void update_ReturnsNotFoundResponse_WhenUserDoesNotExist() {
        UserRequestDto userRequest = new UserRequestDto();

        when(userService.update(userRequest)).thenThrow(EntityNotFoundException.class);

        ResponseEntity<UserResponseDto> response = userController.update(userRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void updatePwd_ReturnsOkResponse_WhenPasswordIsUpdated() {
        UserChangePwdRequestDto userChangePwdRequest = new UserChangePwdRequestDto();
        UserResponseDto userResponse = new UserResponseDto();

        when(userService.updatePwd(userChangePwdRequest)).thenReturn(userResponse);

        ResponseEntity<UserResponseDto> response = userController.updatePwd(userChangePwdRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponse, response.getBody());
    }

    @Test
    void updatePwd_ReturnsNotFoundResponse_WhenUserDoesNotExist() {
        UserChangePwdRequestDto userChangePwdRequest = new UserChangePwdRequestDto();

        when(userService.updatePwd(userChangePwdRequest)).thenThrow(EntityNotFoundException.class);

        ResponseEntity<UserResponseDto> response = userController.updatePwd(userChangePwdRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteProduct_ReturnsOkResponse_WhenUserIsDeleted() {
        Long userId = 1L;

        doNothing().when(userService).delete(userId);

        ResponseEntity response = userController.deleteProduct(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully!.", response.getBody());
    }

    @Test
    void deleteProduct_ReturnsNotFoundResponse_WhenUserDoesNotExist() {
        Long userId = 1L;

        doThrow(EntityNotFoundException.class).when(userService).delete(userId);

        ResponseEntity response = userController.deleteProduct(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
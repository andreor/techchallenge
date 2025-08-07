package com.fiap.techchallenge.controller;

import com.fiap.techchallenge.dto.UserChangePwdRequestDto;
import com.fiap.techchallenge.dto.UserRequestDto;
import com.fiap.techchallenge.dto.UserResponseDto;
import com.fiap.techchallenge.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Create a new user", description = "Add a new user to the system")
    @PostMapping("/create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = UserRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto user) {
        var userCreated = userService.create(user);
        return ResponseEntity.ok(userCreated);
    }

    @Operation(summary = "Update a user", description = "Update an existing user's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(schema = @Schema(implementation = UserRequestDto.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/update")
    public ResponseEntity<UserResponseDto> update(@RequestBody UserRequestDto user) {
        try{
            var userUpdate = userService.update(user);
            return new ResponseEntity <UserResponseDto>(userUpdate, HttpStatus.OK);
        }catch (EntityNotFoundException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update user password", description = "Update an existing user's password")
    @PatchMapping("/updatePwd")
    public ResponseEntity<UserResponseDto> updatePwd(@RequestBody UserChangePwdRequestDto user) {
        try{
            var userUpdate = userService.updatePwd(user);
            return new ResponseEntity <UserResponseDto>(userUpdate, HttpStatus.OK);
        }catch (EntityNotFoundException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a user", description = "Delete a user from the system using their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        try{
             userService.delete(id);
            return ResponseEntity.ok("User deleted successfully!.");
        }catch (EntityNotFoundException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

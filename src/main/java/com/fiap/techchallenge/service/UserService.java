package com.fiap.techchallenge.service;

import com.fiap.techchallenge.dto.UserChangePwdRequestDto;
import com.fiap.techchallenge.dto.UserRequestDto;
import com.fiap.techchallenge.dto.UserResponseDto;
import com.fiap.techchallenge.repository.UserRepository;
import com.fiap.techchallenge.repository.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    public UserResponseDto create(UserRequestDto user) {
        User entity = modelMapper.map(user, User.class);
        entity.setLastModified(LocalDateTime.now());
        userRepository.save(entity);

        return  modelMapper.map(entity, UserResponseDto.class);
    }

    public UserResponseDto update(UserRequestDto user) {
        User entity = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));


        entity = modelMapper.map(user, User.class);

        entity.setLastModified(LocalDateTime.now());
        userRepository.save(entity);
        entity.setLastModified(LocalDateTime.now());
        return  modelMapper.map(entity, UserResponseDto.class);
    }

    public UserResponseDto updatePwd(UserChangePwdRequestDto user) {
        User entity = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));


        entity.setPassword(user.getPassword());
        entity.setLastModified(LocalDateTime.now());
        userRepository.save(entity);
        return  modelMapper.map(entity, UserResponseDto.class);
    }

    public void delete(Long id) {
        User entity =  userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(entity);
    }

}

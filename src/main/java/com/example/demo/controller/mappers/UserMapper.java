package com.example.demo.controller.mappers;

import com.example.demo.controller.data.requests.UserRequestDTO;
import com.example.demo.controller.data.responses.UserResponseDTO;
import com.example.demo.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User map(UserRequestDTO userRequestDTO);
    UserResponseDTO map(User entity);
}
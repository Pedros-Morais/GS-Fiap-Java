package com.fiap.blackoutservice.service;

import java.util.List;

import com.fiap.blackoutservice.dto.UserDTO;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    UserDTO getUserRewards(Long id);
}

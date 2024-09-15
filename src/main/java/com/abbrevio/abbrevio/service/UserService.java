package com.abbrevio.abbrevio.service;

import com.abbrevio.abbrevio.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsersByDepartment(int id);
}

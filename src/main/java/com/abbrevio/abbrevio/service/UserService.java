package com.abbrevio.abbrevio.service;

import com.abbrevio.abbrevio.payload.user.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsersByDepartment(int id);
}

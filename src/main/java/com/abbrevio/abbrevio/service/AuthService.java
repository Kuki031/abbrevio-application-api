package com.abbrevio.abbrevio.service;


import com.abbrevio.abbrevio.dto.LoginDTO;
import com.abbrevio.abbrevio.dto.RegisterDTO;
import com.abbrevio.abbrevio.dto.UserDTO;

public interface AuthService {

    String login(LoginDTO loginDto);
    void register(RegisterDTO registerDto) throws Exception;
    UserDTO getMe();
}

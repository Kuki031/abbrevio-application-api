package com.abbrevio.abbrevio.service;


import com.abbrevio.abbrevio.entity.User;
import com.abbrevio.abbrevio.payload.LoginDTO;
import com.abbrevio.abbrevio.payload.RegisterDTO;
import com.abbrevio.abbrevio.payload.user.UserDTO;
import com.abbrevio.abbrevio.payload.user.UserDetailsDTO;

public interface AuthService {

    String login(LoginDTO loginDto);
    void register(RegisterDTO registerDto) throws Exception;
    UserDetailsDTO getMe();
}

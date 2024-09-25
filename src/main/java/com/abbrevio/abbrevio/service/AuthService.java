package com.abbrevio.abbrevio.service;


import com.abbrevio.abbrevio.dto.LoginDTO;
import com.abbrevio.abbrevio.dto.RegisterDTO;

public interface AuthService {

    String login(LoginDTO loginDto);
    void register(RegisterDTO registerDto) throws Exception;
}

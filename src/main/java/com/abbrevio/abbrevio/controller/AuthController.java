package com.abbrevio.abbrevio.controller;


import com.abbrevio.abbrevio.entity.User;
import com.abbrevio.abbrevio.payload.JWTAuthResponse;
import com.abbrevio.abbrevio.payload.LoginDTO;
import com.abbrevio.abbrevio.payload.RegisterDTO;
import com.abbrevio.abbrevio.payload.user.UserDTO;
import com.abbrevio.abbrevio.payload.user.UserDetailsDTO;
import com.abbrevio.abbrevio.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping(value = {"/login", "/sign-in"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginDto) {
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/register", "/sign-up"})
    public ResponseEntity<HttpStatus> register(@Valid @RequestBody RegisterDTO registerDto) throws Exception {
        authService.register(registerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailsDTO> getMe() {
        return ResponseEntity.ok(authService.getMe());
    }
}


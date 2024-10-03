package com.abbrevio.abbrevio.service.impl;

import com.abbrevio.abbrevio.payload.LoginDTO;
import com.abbrevio.abbrevio.payload.RegisterDTO;
import com.abbrevio.abbrevio.payload.user.UserDTO;
import com.abbrevio.abbrevio.entity.Department;
import com.abbrevio.abbrevio.entity.Role;
import com.abbrevio.abbrevio.entity.User;
import com.abbrevio.abbrevio.exception.CustomAuthException;
import com.abbrevio.abbrevio.exception.CustomNotFoundException;
import com.abbrevio.abbrevio.payload.user.UserDetailsDTO;
import com.abbrevio.abbrevio.repository.DepartmentRepository;
import com.abbrevio.abbrevio.repository.RoleRepository;
import com.abbrevio.abbrevio.repository.UserRepository;
import com.abbrevio.abbrevio.security.JwtTokenProvider;
import com.abbrevio.abbrevio.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String login(LoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public void register(RegisterDTO registerDto) throws CustomAuthException {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new CustomAuthException("username is already taken");
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new CustomAuthException("e-mail already exists");
        }
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        if (registerDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(registerDto.getDepartmentId())
                    .orElseThrow(() -> new CustomNotFoundException(Department.class, "id", registerDto.getDepartmentId()));
            user.setDepartment(department);
            department.setCountOfEmployees();
        }

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
    }

    @Override
    public UserDetailsDTO getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findWithRolesAndDepartmentByUsername(username)
                .orElseThrow(() -> new CustomNotFoundException(User.class, "username", username));

        return modelMapper.map(user, UserDetailsDTO.class);
    }
}

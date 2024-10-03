package com.abbrevio.abbrevio.service.impl;

import com.abbrevio.abbrevio.entity.Department;
import com.abbrevio.abbrevio.entity.User;
import com.abbrevio.abbrevio.exception.CustomNotFoundException;
import com.abbrevio.abbrevio.payload.user.UserDTO;
import com.abbrevio.abbrevio.repository.DepartmentRepository;
import com.abbrevio.abbrevio.repository.UserRepository;
import com.abbrevio.abbrevio.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDTO> getUsersByDepartment(int id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Department.class, "id", id));

        List<User> users = userRepository.findByDepartmentId(department.getId());
        return users.stream().map((user) -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }
}

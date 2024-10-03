package com.abbrevio.abbrevio.service.impl;

import com.abbrevio.abbrevio.payload.DepartmentDTO;
import com.abbrevio.abbrevio.entity.Department;
import com.abbrevio.abbrevio.exception.CustomNotFoundException;
import com.abbrevio.abbrevio.repository.DepartmentRepository;
import com.abbrevio.abbrevio.repository.UserRepository;
import com.abbrevio.abbrevio.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public DepartmentDTO getDepartmentById(Integer id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Department.class, "id", id));
        return modelMapper.map(department, DepartmentDTO.class);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map((department) -> modelMapper.map(department, DepartmentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department newDepartment = new Department();
        newDepartment.setCountOfEmployees(0);
        newDepartment.setName(departmentDTO.getName());
        departmentRepository.save(newDepartment);

        return modelMapper.map(newDepartment, DepartmentDTO.class);
    }

    @Override
    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO, Integer id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Department.class, "id", id));

        if (departmentDTO.getName() != null) {
            department.setName(departmentDTO.getName());
        }

        if (departmentDTO.getCountOfEmployees() > 0) {
            department.setCountOfEmployees(departmentDTO.getCountOfEmployees());
        }
        Department savedDepartment = departmentRepository.save(department);

        return modelMapper.map(savedDepartment, DepartmentDTO.class);
    }

    @Override
    public void deleteDepartment(Integer id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Department.class, "id", id));
        departmentRepository.delete(department);
    }
}

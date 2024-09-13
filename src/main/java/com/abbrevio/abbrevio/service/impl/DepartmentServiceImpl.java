package com.abbrevio.abbrevio.service.impl;

import com.abbrevio.abbrevio.dto.DepartmentDTO;
import com.abbrevio.abbrevio.entity.Department;
import com.abbrevio.abbrevio.repository.DepartmentRepository;
import com.abbrevio.abbrevio.service.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DepartmentDTO getDepartmentById(Integer id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("department does not exist."));
        return modelMapper.map(department, DepartmentDTO.class);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map((department) -> modelMapper.map(department, DepartmentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        return null;
    }

    @Override
    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO, Integer id) {
        return null;
    }

    @Override
    public void deleteDepartment(Integer id) {

    }
}

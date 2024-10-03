package com.abbrevio.abbrevio.service;

import com.abbrevio.abbrevio.payload.DepartmentDTO;

import java.util.List;


public interface DepartmentService {
    DepartmentDTO getDepartmentById(Integer id);
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
    DepartmentDTO updateDepartment(DepartmentDTO departmentDTO, Integer id);
    void deleteDepartment(Integer id);
}

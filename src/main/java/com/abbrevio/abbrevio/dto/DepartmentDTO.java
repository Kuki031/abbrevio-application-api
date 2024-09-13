package com.abbrevio.abbrevio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    private int id;
    @NotBlank(message = "department name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "department name can only contain letters")
    private String name;
    private int countOfEmployees;
}

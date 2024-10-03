package com.abbrevio.abbrevio.payload.user;

import com.abbrevio.abbrevio.entity.Role;
import com.abbrevio.abbrevio.payload.DepartmentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {

    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;
    private DepartmentDTO department;
}

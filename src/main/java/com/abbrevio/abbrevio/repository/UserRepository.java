package com.abbrevio.abbrevio.repository;


import com.abbrevio.abbrevio.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findWithRolesByUsername(String username);

    @EntityGraph(attributePaths = {"roles", "department"})
    Optional<User> findWithRolesAndDepartmentByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<User> findByDepartmentId(int id);
}

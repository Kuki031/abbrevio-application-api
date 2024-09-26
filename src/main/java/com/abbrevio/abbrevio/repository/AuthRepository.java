package com.abbrevio.abbrevio.repository;

import com.abbrevio.abbrevio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User, Long> {
}

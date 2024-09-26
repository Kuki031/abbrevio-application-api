package com.abbrevio.abbrevio.repository;

import com.abbrevio.abbrevio.entity.Abbreviation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbbreviationRepository extends JpaRepository<Abbreviation, Long> {
    List<Abbreviation> findByNameContainingOrderByName(String name);
    List<Abbreviation> findByUserId(Long id);
}

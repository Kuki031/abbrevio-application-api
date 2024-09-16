package com.abbrevio.abbrevio.repository;

import com.abbrevio.abbrevio.entity.Abbreviation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbbreviationRepository extends JpaRepository<Abbreviation, Long> {
}

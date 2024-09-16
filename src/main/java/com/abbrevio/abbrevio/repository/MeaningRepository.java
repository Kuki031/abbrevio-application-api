package com.abbrevio.abbrevio.repository;

import com.abbrevio.abbrevio.entity.Meaning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeaningRepository extends JpaRepository<Meaning, Long> {
}

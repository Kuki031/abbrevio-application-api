package com.abbrevio.abbrevio.repository;

import com.abbrevio.abbrevio.entity.Meaning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface MeaningRepository extends JpaRepository<Meaning, Long> {
    List<Meaning> findByAbbreviationIdOrderByCountOfVotesDesc(Long id);
}

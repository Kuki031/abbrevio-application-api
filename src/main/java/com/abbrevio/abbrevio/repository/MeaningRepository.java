package com.abbrevio.abbrevio.repository;

import com.abbrevio.abbrevio.entity.Meaning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeaningRepository extends JpaRepository<Meaning, Long> {
    List<Meaning> findByAbbreviationIdOrderByCountOfVotesDesc(Long id);
    Meaning findByIdAndAbbreviationId(Long meaningId, Long abbreviationId);
}

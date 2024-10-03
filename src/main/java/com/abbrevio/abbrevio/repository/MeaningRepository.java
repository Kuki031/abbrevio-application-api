package com.abbrevio.abbrevio.repository;

import com.abbrevio.abbrevio.entity.Meaning;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeaningRepository extends JpaRepository<Meaning, Long> {

    @EntityGraph(attributePaths = {"user"})
    List<Meaning> findByAbbreviationIdOrderByCountOfVotesDesc(Long id);

    @EntityGraph(attributePaths = {"user"})
    Meaning findByIdAndAbbreviationId(Long meaningId, Long abbreviationId);
}

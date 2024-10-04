package com.abbrevio.abbrevio.repository;

import com.abbrevio.abbrevio.entity.Abbreviation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AbbreviationRepository extends JpaRepository<Abbreviation, Long> {

    @Override
    @EntityGraph(attributePaths = {"user"})
    List<Abbreviation> findAll();

    List<Abbreviation> findTop10ByOrderByAccessedAtDesc();

    List<Abbreviation> findTop10ByOrderByCreatedAtDesc();

    @Override
    @EntityGraph(attributePaths = {"user"})
    Optional<Abbreviation> findById(Long abbreviationId);

    @EntityGraph(attributePaths = {"user"})
    List<Abbreviation> findByNameContainingOrderByName(String name);

    @EntityGraph(attributePaths = {"user"})
    List<Abbreviation> findByUserId(Long id);

    @EntityGraph(attributePaths = {"user"})
    List<Abbreviation> findByUserUsername(String username);
}

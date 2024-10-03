package com.abbrevio.abbrevio.service;

import com.abbrevio.abbrevio.payload.MeaningDTO;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface MeaningService {

    List<MeaningDTO> findByAbbreviationIdOrderByCountOfVotes(Long id);
    MeaningDTO getMeaningForAbbreviation(Long abbreviationId, Long meaningId);
    MeaningDTO createMeaningForAbbreviation(MeaningDTO meaningDTO, Long id);
    MeaningDTO updateMeaningForAbbreviation(Long abbreviationId, Long meaningId, MeaningDTO meaningDTO) throws Exception;
    void deleteMeaningForAbbreviation(Long abbreviationId, Long meaningId) throws Exception;
}

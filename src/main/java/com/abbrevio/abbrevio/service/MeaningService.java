package com.abbrevio.abbrevio.service;

import com.abbrevio.abbrevio.dto.MeaningDTO;

import java.util.List;

public interface MeaningService {
    List<MeaningDTO> findByAbbreviationIdOrderByCountOfVotes(Long id);
    MeaningDTO getMeaningForAbbreviation(Long abbreviationId, Long meaningId);
    MeaningDTO createMeaningForAbbreviation(MeaningDTO meaningDTO, Long id);
    MeaningDTO updateMeaningForAbbreviation(Long abbreviationId, Long meaningId, MeaningDTO meaningDTO);
    void deleteMeaningForAbbreviation(Long abbreviationId, Long meaningId);
}

package com.abbrevio.abbrevio.service;

import com.abbrevio.abbrevio.dto.MeaningDTO;

import java.util.Set;

public interface MeaningService {
    Set<MeaningDTO> getMeaningsForAbbreviation(Long id);
    MeaningDTO getMeaningForAbbreviation(Long abbreviationId, Long meaningId) throws Exception;
    MeaningDTO createMeaningForAbbreviation(MeaningDTO meaningDTO, Long id);
    MeaningDTO updateMeaningForAbbreviation(Long abbreviationId, Long meaningId, MeaningDTO meaningDTO) throws Exception;
    void deleteMeaningForAbbreviation(Long abbreviationId, Long meaningId) throws Exception;
}

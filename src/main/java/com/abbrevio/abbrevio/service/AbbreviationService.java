package com.abbrevio.abbrevio.service;

import com.abbrevio.abbrevio.dto.AbbreviationDTO;

import java.util.List;

public interface AbbreviationService {
    AbbreviationDTO createAbbreviation(AbbreviationDTO abbreviationDTO);
    List<AbbreviationDTO> getAllAbbreviations();
    AbbreviationDTO getAbbreviationById(Long id);
    AbbreviationDTO updateAbbreviation(Long id, AbbreviationDTO abbreviationDTO) throws Exception;
    void deleteAbbreviation(Long id) throws Exception;

    List<AbbreviationDTO> getAllContainingName(String name);
}

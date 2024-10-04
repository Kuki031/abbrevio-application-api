package com.abbrevio.abbrevio.service;

import com.abbrevio.abbrevio.payload.abbreviation.AbbreviationDTO;
import com.abbrevio.abbrevio.payload.abbreviation.AbbreviationWithUserDTO;

import java.util.List;

public interface AbbreviationService {
    AbbreviationDTO createAbbreviation(AbbreviationDTO abbreviationDTO);
    List<AbbreviationWithUserDTO> getAllAbbreviations();
    AbbreviationDTO getAbbreviationById(Long id);
    AbbreviationDTO updateAbbreviation(Long id, AbbreviationDTO abbreviationDTO) throws Exception;
    void deleteAbbreviation(Long id) throws Exception;
    List<AbbreviationWithUserDTO> getAllContainingName(String name);
    List<AbbreviationWithUserDTO> getMyAbbreviations();
    List<AbbreviationDTO> getTopTenSearches();
    List<AbbreviationWithUserDTO> getRecentlyAdded();
}

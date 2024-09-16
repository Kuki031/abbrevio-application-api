package com.abbrevio.abbrevio.service.impl;

import com.abbrevio.abbrevio.dto.AbbreviationDTO;
import com.abbrevio.abbrevio.entity.Abbreviation;
import com.abbrevio.abbrevio.exception.CustomNotFoundException;
import com.abbrevio.abbrevio.repository.AbbreviationRepository;
import com.abbrevio.abbrevio.service.AbbreviationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbbreviationServiceImpl implements AbbreviationService {

    private final AbbreviationRepository abbreviationRepository;
    private final ModelMapper modelMapper;

    public AbbreviationServiceImpl(AbbreviationRepository abbreviationRepository, ModelMapper modelMapper) {
        this.abbreviationRepository = abbreviationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AbbreviationDTO createAbbreviation(AbbreviationDTO abbreviationDTO) {
        Abbreviation abbreviation = new Abbreviation();
        abbreviation.setName(abbreviationDTO.getName());
        abbreviationRepository.save(abbreviation);

        return modelMapper.map(abbreviation, AbbreviationDTO.class);
    }

    @Override
    public List<AbbreviationDTO> getAllAbbreviations() {
        List<Abbreviation> abbreviations = abbreviationRepository.findAll();
        return abbreviations.stream().map((abbrev) -> modelMapper.map(abbrev, AbbreviationDTO.class)).collect(Collectors.toList());
    }

    @Override
    public AbbreviationDTO getAbbreviationById(Long id) {
        Abbreviation abbreviation = abbreviationRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Abbreviation.class, "id", id));
        return modelMapper.map(abbreviation, AbbreviationDTO.class);
    }

    @Override
    public AbbreviationDTO updateAbbreviation(Long id, AbbreviationDTO abbreviationDTO) {
        Abbreviation abbreviation = abbreviationRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Abbreviation.class, "id", id));

        if (abbreviationDTO.getName() != null) {
            abbreviation.setName(abbreviationDTO.getName());
        }
        abbreviationRepository.save(abbreviation);
        return modelMapper.map(abbreviation, AbbreviationDTO.class);
    }

    @Override
    public void deleteAbbreviation(Long id) {
        Abbreviation abbreviation = abbreviationRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Abbreviation.class, "id", id));
        abbreviationRepository.delete(abbreviation);
    }
}

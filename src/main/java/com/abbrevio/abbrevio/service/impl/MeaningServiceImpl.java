package com.abbrevio.abbrevio.service.impl;

import com.abbrevio.abbrevio.dto.MeaningDTO;
import com.abbrevio.abbrevio.entity.Abbreviation;
import com.abbrevio.abbrevio.entity.Meaning;
import com.abbrevio.abbrevio.exception.CustomNotFoundException;
import com.abbrevio.abbrevio.repository.AbbreviationRepository;
import com.abbrevio.abbrevio.repository.MeaningRepository;
import com.abbrevio.abbrevio.service.MeaningService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MeaningServiceImpl implements MeaningService {

    private final MeaningRepository meaningRepository;
    private final AbbreviationRepository abbreviationRepository;
    private final ModelMapper modelMapper;

    public MeaningServiceImpl(MeaningRepository meaningRepository, ModelMapper modelMapper, AbbreviationRepository abbreviationRepository) {
        this.meaningRepository = meaningRepository;
        this.modelMapper = modelMapper;
        this.abbreviationRepository = abbreviationRepository;
    }

    @Override
    public Set<MeaningDTO> getMeaningsForAbbreviation(Long id) {
        Abbreviation abbreviation = abbreviationRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Abbreviation.class, "id", id));

        Set<Meaning> meanings = abbreviation.getMeanings();

        return meanings.stream().map((meaning) -> modelMapper.map(meaning, MeaningDTO.class)).collect(Collectors.toSet());
    }

    @Override
    public MeaningDTO getMeaningForAbbreviation(Long abbreviationId, Long meaningId) throws Exception {

        Abbreviation abbreviation = abbreviationRepository.findById(abbreviationId)
                .orElseThrow(() -> new CustomNotFoundException(Abbreviation.class, "id", abbreviationId));

        Meaning meaning = meaningRepository.findById(meaningId)
                .orElseThrow(() -> new CustomNotFoundException(Meaning.class, "id", meaningId));

        if (!meaning.getAbbreviation().getId().equals(abbreviation.getId()))
        {
            throw new Exception(String.format("meaning with id '%s' does not belong to abbreviation with id '%s'", meaningId, abbreviationId));
        }

        return modelMapper.map(meaning, MeaningDTO.class);
    }

    @Override
    public MeaningDTO createMeaningForAbbreviation(MeaningDTO meaningDTO, Long id) {
        Abbreviation abbreviation = abbreviationRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Abbreviation.class, "id", id));

        Meaning meaning = new Meaning();
        meaning.setDescription(meaningDTO.getDescription());
        meaning.setAbbreviation(abbreviation);
        meaning.setCountOfVotes((long) 0);
        meaningRepository.save(meaning);

        return modelMapper.map(meaning, MeaningDTO.class);
    }

    @Override
    public MeaningDTO updateMeaningForAbbreviation(Long abbreviationId, Long meaningId, MeaningDTO meaningDTO) throws Exception {
        Abbreviation abbreviation = abbreviationRepository.findById(abbreviationId)
                .orElseThrow(() -> new CustomNotFoundException(Abbreviation.class, "id", abbreviationId));

        Meaning meaning = meaningRepository.findById(meaningId)
                .orElseThrow(() -> new CustomNotFoundException(Meaning.class, "id", meaningId));

        if (!meaning.getAbbreviation().getId().equals(abbreviation.getId()))
        {
            throw new Exception(String.format("meaning with id '%s' does not belong to abbreviation with id '%s'", meaningId, abbreviationId));
        }

        if (meaningDTO.getDescription() != null) {
            meaning.setDescription(meaningDTO.getDescription());
        }

        meaningRepository.save(meaning);

        return modelMapper.map(meaning, MeaningDTO.class);
    }

    @Override
    public void deleteMeaningForAbbreviation(Long abbreviationId, Long meaningId) throws Exception {
        Abbreviation abbreviation = abbreviationRepository.findById(abbreviationId)
                .orElseThrow(() -> new CustomNotFoundException(Abbreviation.class, "id", abbreviationId));

        Meaning meaning = meaningRepository.findById(meaningId)
                .orElseThrow(() -> new CustomNotFoundException(Meaning.class, "id", meaningId));

        if (!meaning.getAbbreviation().getId().equals(abbreviation.getId()))
        {
            throw new Exception(String.format("meaning with id '%s' does not belong to abbreviation with id '%s'", meaningId, abbreviationId));
        }

        meaningRepository.delete(meaning);
    }
}

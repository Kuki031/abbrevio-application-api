package com.abbrevio.abbrevio.service.impl;

import com.abbrevio.abbrevio.entity.Abbreviation;
import com.abbrevio.abbrevio.entity.User;
import com.abbrevio.abbrevio.exception.CustomNotFoundException;
import com.abbrevio.abbrevio.payload.AbbreviationDTO;
import com.abbrevio.abbrevio.repository.AbbreviationRepository;
import com.abbrevio.abbrevio.repository.UserRepository;
import com.abbrevio.abbrevio.service.AbbreviationService;
import com.abbrevio.abbrevio.utils.AuthRetrieval;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbbreviationServiceImpl implements AbbreviationService {

    private final AbbreviationRepository abbreviationRepository;
    private final ModelMapper modelMapper;
    private final AuthRetrieval authRetrieval;

    public AbbreviationServiceImpl(AbbreviationRepository abbreviationRepository, ModelMapper modelMapper, UserRepository userRepository, AuthRetrieval authRetrieval) {
        this.abbreviationRepository = abbreviationRepository;
        this.modelMapper = modelMapper;
        this.authRetrieval = authRetrieval;
    }

    @Override
    public AbbreviationDTO createAbbreviation(AbbreviationDTO abbreviationDTO) {

        User user = (User) authRetrieval.retrieveUsername(true);
        Abbreviation abbreviation = new Abbreviation();
        abbreviation.setName(abbreviationDTO.getName());
        abbreviation.setUser(user);
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
    public AbbreviationDTO updateAbbreviation(Long id, AbbreviationDTO abbreviationDTO) throws Exception {
        Abbreviation abbreviation = abbreviationRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Abbreviation.class, "id", id));

        User user = (User) authRetrieval.retrieveUsername(true);

        if (!abbreviation.getUser().getId().equals(user.getId())) {
            throw new Exception(String.format("you cannot edit abbreviation with id %s, because you have not created it", id));
        }


        if (abbreviationDTO.getName() != null) {
            abbreviation.setName(abbreviationDTO.getName());
        }
        abbreviationRepository.save(abbreviation);
        return modelMapper.map(abbreviation, AbbreviationDTO.class);
    }

    @Override
    public void deleteAbbreviation(Long id) throws Exception {
        Abbreviation abbreviation = abbreviationRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Abbreviation.class, "id", id));
        User user = (User) authRetrieval.retrieveUsername(true);

        if (!abbreviation.getUser().getId().equals(user.getId())) {
            throw new Exception(String.format("you cannot delete abbreviation with id %s, because you have not created it", id));
        }
        abbreviationRepository.delete(abbreviation);
    }

    @Override
    public List<AbbreviationDTO> getAllContainingName(String name) {
        List<Abbreviation> abbreviations = abbreviationRepository.findByNameContainingOrderByName(name);

        return abbreviations.stream().map((abbreviation -> modelMapper.map(abbreviation, AbbreviationDTO.class))).collect(Collectors.toList());
    }

    @Override
    public List<AbbreviationDTO> getMyAbbreviations() {
        String username = (String) authRetrieval.retrieveUsername(false);
        List<Abbreviation> abbreviations = abbreviationRepository.findByUserUsername(username);
        return abbreviations.stream().map((abbrev) -> modelMapper.map(abbrev, AbbreviationDTO.class)).collect(Collectors.toList());
    }
}

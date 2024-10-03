package com.abbrevio.abbrevio.service.impl;

import com.abbrevio.abbrevio.payload.MeaningDTO;
import com.abbrevio.abbrevio.entity.Abbreviation;
import com.abbrevio.abbrevio.entity.Meaning;
import com.abbrevio.abbrevio.entity.User;
import com.abbrevio.abbrevio.exception.CustomNotFoundException;
import com.abbrevio.abbrevio.repository.AbbreviationRepository;
import com.abbrevio.abbrevio.repository.MeaningRepository;
import com.abbrevio.abbrevio.repository.UserRepository;
import com.abbrevio.abbrevio.service.MeaningService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeaningServiceImpl implements MeaningService {

    private final MeaningRepository meaningRepository;
    private final AbbreviationRepository abbreviationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public MeaningServiceImpl(MeaningRepository meaningRepository, ModelMapper modelMapper, AbbreviationRepository abbreviationRepository, UserRepository userRepository) {
        this.meaningRepository = meaningRepository;
        this.modelMapper = modelMapper;
        this.abbreviationRepository = abbreviationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<MeaningDTO> findByAbbreviationIdOrderByCountOfVotes(Long id) {

        Abbreviation abbreviation = abbreviationRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Abbreviation.class, "id", id));

        List<Meaning> meanings = meaningRepository.findByAbbreviationIdOrderByCountOfVotesDesc(id);
        return meanings.stream().map((meaning) -> modelMapper.map(meaning, MeaningDTO.class)).collect(Collectors.toList());
    }

    @Override
    public MeaningDTO getMeaningForAbbreviation(Long abbreviationId, Long meaningId) {

        Meaning meaning = meaningRepository.findByIdAndAbbreviationId(meaningId, abbreviationId);
        if (meaning == null)
        {
            throw new CustomNotFoundException(Meaning.class, "id", meaningId);
        }

        return modelMapper.map(meaning, MeaningDTO.class);
    }

    @Override
    public MeaningDTO createMeaningForAbbreviation(MeaningDTO meaningDTO, Long id) {

        Abbreviation abbreviation = abbreviationRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Abbreviation.class, "id", id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new CustomNotFoundException(User.class, "username", authentication.getName()));

        Meaning meaning = new Meaning();
        meaning.setDescription(meaningDTO.getDescription());
        meaning.setAbbreviation(abbreviation);
        meaning.setUser(user);
        meaning.setCountOfVotesInitially();
        meaningRepository.save(meaning);

        return modelMapper.map(meaning, MeaningDTO.class);
    }

    @Override
    public MeaningDTO updateMeaningForAbbreviation(Long abbreviationId, Long meaningId, MeaningDTO meaningDTO) throws Exception {
        Meaning meaning = meaningRepository.findByIdAndAbbreviationId(meaningId, abbreviationId);

        if (meaning == null)
        {
            throw new CustomNotFoundException(Meaning.class, "id", meaningId);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new CustomNotFoundException(User.class, "username", authentication.getName()));

        if (!meaning.getUser().getId().equals(user.getId()))
        {
            throw new Exception(String.format("you can't edit meaning with id %s, because you haven't created it", meaning.getId()));
        }

        if (meaningDTO.getDescription() != null) {
            meaning.setDescription(meaningDTO.getDescription());
        }

        meaningRepository.save(meaning);

        return modelMapper.map(meaning, MeaningDTO.class);
    }

    @Override
    public void deleteMeaningForAbbreviation(Long abbreviationId, Long meaningId) throws Exception {
        Meaning meaning = meaningRepository.findByIdAndAbbreviationId(meaningId, abbreviationId);

        if (meaning == null)
        {
            throw new CustomNotFoundException(Meaning.class, "id", meaningId);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new CustomNotFoundException(User.class, "username", authentication.getName()));
        if (!meaning.getUser().getId().equals(user.getId()))
        {
            throw new Exception(String.format("you can't edit meaning with id %s, because you haven't created it", meaning.getId()));
        }

        meaningRepository.delete(meaning);
    }
}

package com.abbrevio.abbrevio.service.impl;

import com.abbrevio.abbrevio.payload.VoteDTO;
import com.abbrevio.abbrevio.entity.Meaning;
import com.abbrevio.abbrevio.entity.User;
import com.abbrevio.abbrevio.entity.Vote;
import com.abbrevio.abbrevio.exception.CustomNotFoundException;
import com.abbrevio.abbrevio.repository.MeaningRepository;
import com.abbrevio.abbrevio.repository.UserRepository;
import com.abbrevio.abbrevio.repository.VoteRepository;
import com.abbrevio.abbrevio.service.VoteService;
import com.abbrevio.abbrevio.utils.AuthRetrieval;
import com.abbrevio.abbrevio.utils.VoteId;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {


    private final UserRepository userRepository;
    private final MeaningRepository meaningRepository;
    private final VoteId voteId;
    private final VoteRepository voteRepository;
    private final ModelMapper modelMapper;
    private final AuthRetrieval authRetrieval;

    public VoteServiceImpl(AuthRetrieval authRetrieval, UserRepository userRepository, MeaningRepository meaningRepository, VoteId voteId, VoteRepository voteRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
        this.meaningRepository = meaningRepository;
        this.modelMapper = modelMapper;
        this.voteId = voteId;
        this.authRetrieval = authRetrieval;
    }

    @Override
    public void castVote(Long meaningId) {

        User user = (User) authRetrieval.retrieveUsername(true);
        Meaning meaning = meaningRepository.findById(meaningId)
                .orElseThrow(() -> new CustomNotFoundException(Meaning.class, "id", meaningId));

        VoteId voteId = new VoteId(user.getId(), meaning.getId());

        if(!voteRepository.existsById(voteId))
        {
            Vote vote = new Vote();
            vote.setId(voteId);
            vote.setUser(user);
            vote.setMeaning(meaning);
            voteRepository.save(vote);
            meaning.setCountOfVotes(meaning.getCountOfVotes() + 1);

            meaningRepository.save(meaning);
        }
        else
        {
            throw new IllegalStateException("you have already voted for this meaning");
        }
    }

    @Override
    public void castUnVote(Long meaningId) throws Exception {

        User user = (User) authRetrieval.retrieveUsername(true);
        Meaning meaning = meaningRepository.findById(meaningId)
                .orElseThrow(() -> new CustomNotFoundException(Meaning.class, "id", meaningId));

        Vote vote = voteRepository.findByMeaningIdAndUserId(meaningId, user.getId());

        if (vote == null)
        {
            throw new Exception(String.format("User with id: %s never voted for meaning with id: %s", user.getId(), meaning.getId()));
        }
        meaning.setCountOfVotes(meaning.getCountOfVotes() - 1);
        voteRepository.delete(vote);
    }

    @Override
    public VoteDTO getVoteByMeaningIdAndUserId(Long meaningId) throws Exception {

        User user = (User) authRetrieval.retrieveUsername(true);
        Meaning meaning = meaningRepository.findById(meaningId)
                .orElseThrow(() -> new CustomNotFoundException(Meaning.class, "id", meaningId));

        Vote vote = voteRepository.findByMeaningIdAndUserId(meaning.getId(), user.getId());

        if (vote == null)
        {
            throw new Exception(String.format("vote does not exist"));
        }
        return modelMapper.map(vote, VoteDTO.class);
    }
}

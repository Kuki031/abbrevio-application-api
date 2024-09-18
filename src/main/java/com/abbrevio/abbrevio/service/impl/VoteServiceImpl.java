package com.abbrevio.abbrevio.service.impl;

import com.abbrevio.abbrevio.entity.Meaning;
import com.abbrevio.abbrevio.entity.User;
import com.abbrevio.abbrevio.entity.Vote;
import com.abbrevio.abbrevio.exception.CustomNotFoundException;
import com.abbrevio.abbrevio.repository.MeaningRepository;
import com.abbrevio.abbrevio.repository.UserRepository;
import com.abbrevio.abbrevio.repository.VoteRepository;
import com.abbrevio.abbrevio.service.VoteService;
import com.abbrevio.abbrevio.utils.VoteId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {


    private final UserRepository userRepository;
    private final MeaningRepository meaningRepository;
    private final VoteId voteId;
    private final VoteRepository voteRepository;

    public VoteServiceImpl(UserRepository userRepository, MeaningRepository meaningRepository, VoteId voteId, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
        this.meaningRepository = meaningRepository;
        this.voteId = voteId;
    }

    @Override
    public void castVote(Long meaningId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomNotFoundException(User.class, "username", username));

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
            meaning.setCountOfVotes();

            meaningRepository.save(meaning);
        }
        else
        {
            throw new IllegalStateException("you have already voted for this meaning");
        }
    }
}

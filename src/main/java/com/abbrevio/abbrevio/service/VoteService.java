package com.abbrevio.abbrevio.service;

import com.abbrevio.abbrevio.dto.VoteDTO;

public interface VoteService {

    void castVote(Long meaningId);
    VoteDTO getVoteByMeaningIdAndUserId(Long meaningId) throws Exception;
}

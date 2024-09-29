package com.abbrevio.abbrevio.service;

import com.abbrevio.abbrevio.dto.VoteDTO;

public interface VoteService {

    void castVote(Long meaningId);
    void castUnVote(Long meaningId) throws Exception;
    VoteDTO getVoteByMeaningIdAndUserId(Long meaningId) throws Exception;
}

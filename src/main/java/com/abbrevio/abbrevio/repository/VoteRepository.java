package com.abbrevio.abbrevio.repository;

import com.abbrevio.abbrevio.entity.Vote;
import com.abbrevio.abbrevio.utils.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, VoteId> {
}

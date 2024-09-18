package com.abbrevio.abbrevio.repository;

import com.abbrevio.abbrevio.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByIdAndMeaningId(Long commentId, Long meaningId);
}

package com.abbrevio.abbrevio.service;

import com.abbrevio.abbrevio.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createCommentForMeaning(Long meaningId, CommentDTO commentDTO);
    List<CommentDTO> getAllCommentsForMeaning(Long meaningId);
    CommentDTO getCommentForMeaningById(Long meaningId, Long commentId) throws Exception;
    CommentDTO updateCommentForMeaningById(Long meaningId, Long commentId, CommentDTO commentDTO) throws Exception;
    void deleteCommentForMeaningById(Long meaningId, Long commentId) throws Exception;
}

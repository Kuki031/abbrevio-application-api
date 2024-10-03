package com.abbrevio.abbrevio.service;

import com.abbrevio.abbrevio.payload.comment.CommentDTO;
import com.abbrevio.abbrevio.payload.comment.CommentDetailsDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createCommentForMeaning(Long meaningId, CommentDTO commentDTO);
    List<CommentDetailsDTO> getAllCommentsForMeaning(Long meaningId);
    CommentDTO getCommentForMeaningById(Long meaningId, Long commentId);
    CommentDTO updateCommentForMeaningById(Long meaningId, Long commentId, CommentDTO commentDTO) throws Exception;
    void deleteCommentForMeaningById(Long meaningId, Long commentId) throws Exception;
}

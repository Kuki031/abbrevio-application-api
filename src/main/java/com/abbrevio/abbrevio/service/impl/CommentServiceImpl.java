package com.abbrevio.abbrevio.service.impl;

import com.abbrevio.abbrevio.entity.Comment;
import com.abbrevio.abbrevio.entity.Meaning;
import com.abbrevio.abbrevio.entity.User;
import com.abbrevio.abbrevio.exception.CustomNotFoundException;
import com.abbrevio.abbrevio.payload.comment.CommentDTO;
import com.abbrevio.abbrevio.payload.comment.CommentDetailsDTO;
import com.abbrevio.abbrevio.repository.CommentRepository;
import com.abbrevio.abbrevio.repository.MeaningRepository;
import com.abbrevio.abbrevio.repository.UserRepository;
import com.abbrevio.abbrevio.service.CommentService;
import com.abbrevio.abbrevio.utils.AuthRetrieval;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MeaningRepository meaningRepository;
    private final ModelMapper modelMapper;
    private final AuthRetrieval authRetrieval;

    public CommentServiceImpl(AuthRetrieval authRetrieval, CommentRepository commentRepository, ModelMapper modelMapper, UserRepository userRepository, MeaningRepository meaningRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.meaningRepository = meaningRepository;
        this.authRetrieval = authRetrieval;
    }

    @Override
    public CommentDTO createCommentForMeaning(Long meaningId, CommentDTO commentDTO) {

        User user = (User) authRetrieval.retrieveUsername(true);

        Meaning meaning = meaningRepository.findById(meaningId)
                .orElseThrow(() -> new CustomNotFoundException(Meaning.class, "id", meaningId));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setMeaning(meaning);
        comment.setContent(commentDTO.getContent());
        commentRepository.save(comment);

        return modelMapper.map(comment, CommentDTO.class);
    }

    @Override
    public List<CommentDetailsDTO> getAllCommentsForMeaning(Long meaningId) {

        List<Comment> comments = commentRepository.findWithUserByMeaningIdOrderByCreatedAtDesc(meaningId);
        return comments.stream().map((comment) -> modelMapper.map(comment, CommentDetailsDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentForMeaningById(Long meaningId, Long commentId) {
        Comment comment = commentRepository.findByIdAndMeaningId(commentId, meaningId);
        if (comment == null)
        {
            throw new CustomNotFoundException(Comment.class, "id", commentId);
        }
        return modelMapper.map(comment, CommentDTO.class);
    }

    @Override
    public CommentDTO updateCommentForMeaningById(Long meaningId, Long commentId, CommentDTO commentDTO) throws Exception {

        User user = (User) authRetrieval.retrieveUsername(true);

        Comment comment = commentRepository.findByIdAndMeaningId(commentId, meaningId);

        if (comment == null)
        {
            throw new CustomNotFoundException(Comment.class, "id", commentId);
        }

        if (!comment.getUser().getId().equals(user.getId()))
        {
            throw new Exception(String.format("you cannot edit comment with id %s, because you have not created it", commentId));
        }

        if (commentDTO.getContent() != null)
        {
            comment.setContent(commentDTO.getContent());
        }
        commentRepository.save(comment);

        return modelMapper.map(comment, CommentDTO.class);
    }

    @Override
    public void deleteCommentForMeaningById(Long meaningId, Long commentId) throws Exception {

        User user = (User) authRetrieval.retrieveUsername(true);

        Comment comment = commentRepository.findByIdAndMeaningId(commentId, meaningId);
        if (comment == null)
        {
            throw new CustomNotFoundException(Comment.class, "id", commentId);
        }

        if (!comment.getUser().getId().equals(user.getId()))
        {
            throw new Exception(String.format("you cannot delete comment with id %s, because you have not created it", commentId));
        }

        commentRepository.delete(comment);
    }
}

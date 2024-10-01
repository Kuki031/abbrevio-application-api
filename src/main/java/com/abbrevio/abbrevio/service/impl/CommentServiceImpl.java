package com.abbrevio.abbrevio.service.impl;

import com.abbrevio.abbrevio.dto.CommentDTO;
import com.abbrevio.abbrevio.entity.Comment;
import com.abbrevio.abbrevio.entity.Meaning;
import com.abbrevio.abbrevio.entity.User;
import com.abbrevio.abbrevio.exception.CustomNotFoundException;
import com.abbrevio.abbrevio.repository.CommentRepository;
import com.abbrevio.abbrevio.repository.MeaningRepository;
import com.abbrevio.abbrevio.repository.UserRepository;
import com.abbrevio.abbrevio.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MeaningRepository meaningRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, UserRepository userRepository, MeaningRepository meaningRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.meaningRepository = meaningRepository;
    }

    @Override
    public CommentDTO createCommentForMeaning(Long meaningId, CommentDTO commentDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomNotFoundException(User.class, "username", username));
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
    public List<CommentDTO> getAllCommentsForMeaning(Long meaningId) {

        List<Comment> comments = commentRepository.findByMeaningIdOrderByCreatedAtDesc(meaningId);
        return comments.stream().map((comment) -> modelMapper.map(comment, CommentDTO.class)).collect(Collectors.toList());
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomNotFoundException(User.class, "username", username));

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomNotFoundException(User.class, "username", username));

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

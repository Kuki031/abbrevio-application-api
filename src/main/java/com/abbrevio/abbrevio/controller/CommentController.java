package com.abbrevio.abbrevio.controller;

import com.abbrevio.abbrevio.dto.CommentDTO;
import com.abbrevio.abbrevio.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meanings/{meaningId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createCommentForMeaning(@PathVariable Long meaningId, @Valid @RequestBody CommentDTO commentDTO)
    {
        return new ResponseEntity<>(commentService.createCommentForMeaning(meaningId, commentDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllCommentsForMeaning(@PathVariable Long meaningId)
    {
        return ResponseEntity.ok(commentService.getAllCommentsForMeaning(meaningId));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getCommentForMeaning(@PathVariable Long meaningId, @PathVariable Long commentId)
    {
        return ResponseEntity.ok(commentService.getCommentForMeaningById(meaningId, commentId));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateCommentForMeaning(@PathVariable Long meaningId, @PathVariable Long commentId, @Valid @RequestBody CommentDTO commentDTO) throws Exception
    {
        return ResponseEntity.ok(commentService.updateCommentForMeaningById(meaningId, commentId, commentDTO));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<HttpStatus> deleteCommentForMeaning(@PathVariable Long meaningId, @PathVariable Long commentId) throws Exception
    {
        commentService.deleteCommentForMeaningById(meaningId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

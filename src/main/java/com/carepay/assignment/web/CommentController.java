package com.carepay.assignment.web;

import com.carepay.assignment.domain.comments.CommentDetails;
import com.carepay.assignment.domain.comments.CommentRequest;
import com.carepay.assignment.exceptions.InvalidCommentException;
import com.carepay.assignment.helpers.APIConstants;
import com.carepay.assignment.helpers.CommentMappers;
import com.carepay.assignment.helpers.PostMappers;
import com.carepay.assignment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CommentDetails postComment(
            @Valid @RequestBody CommentRequest commentRequest,
            @PathVariable long postId) {
        if (commentRequest.getComment() == null)
            throw new InvalidCommentException(APIConstants.INVALID_COMMENT);
        return commentService.postComment(commentRequest, postId);
    }

    @GetMapping
    public ResponseEntity<Object> getComments(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> pageSize,
            @PathVariable long postId) {
        Page<CommentDetails> commentDetails = commentService.getComments(
                PageRequest.of(
                        page.isPresent() && page.get() > 0 ?
                                page.get() - 1 : page.orElse(APIConstants.DEFAULT_PAGE_NUMBER),
                        pageSize.orElse(APIConstants.DEFAULT_PAGE_SIZE)), postId);
        return new ResponseEntity<>(CommentMappers.mapComments(commentDetails), HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    CommentDetails getComment(@PathVariable long postId,
                              @PathVariable long commentId) {
        return commentService.getComment(postId, commentId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteComment(@PathVariable long commentId) {
        commentService.deleteComment(commentId);
    }
}

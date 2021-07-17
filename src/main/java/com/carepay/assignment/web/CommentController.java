package com.carepay.assignment.web;

import com.carepay.assignment.domain.comments.CommentDetails;
import com.carepay.assignment.domain.comments.CommentRequest;
import com.carepay.assignment.exceptions.InvalidCommentException;
import com.carepay.assignment.helpers.APIConstants;
import com.carepay.assignment.helpers.CommentMappers;
import com.carepay.assignment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    /**
     * Creates a new comment for an existing blog post
     * @param commentRequest the comment body
     * @param postId the ID of the blog post to which a comment is being created
     * @return CommentDetails of the created comment
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CommentDetails postComment(
            @Valid @RequestBody CommentRequest commentRequest,
            @PathVariable long postId) {
        if (commentRequest.getComment() == null)
            throw new InvalidCommentException(APIConstants.INVALID_COMMENT);
        return commentService.postComment(commentRequest, postId);
    }

    /**
     * Retrieves all comments related to a given blog post
     * @param page the currently showing page number
     * @param pageSize the number of comments showing on the current page
     * @param postId the blog post whose comments are being retrieved
     * @return
     */
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

    /**
     * Retrieves an existing comment for a particular blog post using the comment ID
     * @param postId the blog post whose comment is being retrieved
     * @param commentId the ID of the comment being retrieved
     * @return CommentDetails of the retrieved comment
     */
    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    CommentDetails getComment(@PathVariable long postId,
                              @PathVariable long commentId) {
        return commentService.getComment(postId, commentId);
    }

    /**
     * Deletes an existing comment for a particular blog post
     * @param postId the blog post whose comment is being deleted
     * @param commentId the ID of the comment being deleted
     */
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteComment(@PathVariable long postId,
                       @PathVariable long commentId) {
        commentService.deleteComment(postId, commentId);
    }
}

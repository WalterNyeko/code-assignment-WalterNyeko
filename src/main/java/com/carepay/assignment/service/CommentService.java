package com.carepay.assignment.service;

import com.carepay.assignment.domain.comments.CommentDetails;
import com.carepay.assignment.domain.comments.CommentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.validation.Valid;

public interface CommentService {
    CommentDetails postComment(@Valid CommentRequest commentRequest, long postId);
    Page<CommentDetails> getComments(Pageable pageable, long postId);
    CommentDetails getComment(long postId, long commentId);
    void deleteComment(Long commentId);
}

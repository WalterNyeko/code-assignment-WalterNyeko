package com.carepay.assignment.service;

import com.carepay.assignment.domain.comments.Comment;
import com.carepay.assignment.domain.comments.CommentDetails;
import com.carepay.assignment.domain.comments.CommentRequest;
import com.carepay.assignment.domain.posts.Post;
import com.carepay.assignment.domain.posts.PostInfo;
import com.carepay.assignment.exceptions.CommentNotFoundException;
import com.carepay.assignment.exceptions.PostNotFoundException;
import com.carepay.assignment.helpers.APIConstants;
import com.carepay.assignment.repository.CommentRepository;
import com.carepay.assignment.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Override
    public CommentDetails postComment(
            @Valid CommentRequest commentRequest,
            long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            try {
                Post post = optionalPost.get();
                Comment comment = new Comment();
                comment.setComment(commentRequest.getComment());
                comment.setPost(post);
                commentRepository.save(comment);
                return new CommentDetails(comment.getId(), postId, comment.getComment());
            } catch (DataIntegrityViolationException e) {
                String message = e.getCause().getCause().getMessage();
                throw new DataIntegrityViolationException(message);
            }catch (Exception e) {
                String message = e.getCause().getCause().getCause().getLocalizedMessage();
                throw new IllegalArgumentException(message);
            }
        }
        throw new PostNotFoundException(APIConstants.POST_NOT_FOUND);
    }

    @Override
    public Page<CommentDetails> getComments(Pageable pageable, long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Page<Comment> comments = commentRepository.findCommentByPost(pageable, optionalPost.get());
            List<Comment> listOfComments = comments.getContent();
            List<CommentDetails> commentDetails = new ArrayList<>();
            listOfComments.stream().forEach(comment -> {
                commentDetails.add(new CommentDetails(
                        comment.getId(),
                        comment.getPost().getId(),
                        comment.getComment()));
            });
            return new PageImpl<>(commentDetails, pageable, comments.getTotalElements());
        }
        throw new PostNotFoundException(APIConstants.POST_NOT_FOUND);
    }

    @Override
    public CommentDetails getComment(long postId, long commentId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            List<Comment> comments = commentRepository.findCommentByPost(postOptional.get());

            for (Comment comment : comments) {
                if (comment.getId() == commentId) {
                    return new CommentDetails(
                            commentId,
                            comment.getPost().getId(),
                            comment.getComment()
                    );
                }
            }
            throw new CommentNotFoundException(APIConstants.COMMENT_NOT_FOUND);
        }
        throw new PostNotFoundException(APIConstants.POST_NOT_FOUND);
    }

    @Override
    public void deleteComment(Long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            try {
                commentRepository.deleteById(commentId);
                return;
            }catch (Exception e) {
                throw new IllegalArgumentException(e.getCause().getCause());
            }
        }
        throw new CommentNotFoundException(APIConstants.COMMENT_NOT_FOUND);
    }
}

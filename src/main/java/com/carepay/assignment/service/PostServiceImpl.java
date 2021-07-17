package com.carepay.assignment.service;

import javax.validation.Valid;
import com.carepay.assignment.domain.comments.Comment;
import com.carepay.assignment.domain.comments.CommentInfo;
import com.carepay.assignment.domain.posts.CreatePostRequest;
import com.carepay.assignment.domain.posts.Post;
import com.carepay.assignment.domain.posts.PostDetails;
import com.carepay.assignment.domain.posts.PostInfo;
import com.carepay.assignment.exceptions.PostAlreadyExistsException;
import com.carepay.assignment.exceptions.PostNotFoundException;
import com.carepay.assignment.helpers.APIConstants;
import com.carepay.assignment.helpers.PostMappers;
import com.carepay.assignment.repository.CommentRepository;
import com.carepay.assignment.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Override
    public PostDetails createPost(@Valid CreatePostRequest createPostRequest) {
        Post post = new Post();
        PostDetails postDetails = new PostDetails();
        try {
            post.setContent(createPostRequest.getContent());
            post.setTitle(createPostRequest.getTitle());
            postRepository.save(post);
            postDetails = PostMappers.mapPostDetails(post, null);
        }catch (DataIntegrityViolationException e) {
            String message = e.getCause().getCause().getMessage();
            if (message.contains(APIConstants.UNIQUE_INDEX_VIOLATION))
                throw new PostAlreadyExistsException(APIConstants.TITLE, post.getTitle());
            throw new DataIntegrityViolationException(message);
        }catch (Exception e) {
            throw new IllegalArgumentException(e.getCause().getCause());
        }
        return postDetails;
    }

    @Override
    public Page<PostInfo> getPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostInfo> postInfos = new ArrayList<>();
        listOfPosts.stream().forEach(post -> {
            postInfos.add(new PostInfo(post.getId(), post.getTitle()));
        });
        return new PageImpl<>(postInfos, pageable, posts.getTotalElements());
    }

    @Override
    public PostDetails getPostDetails(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            List<Comment> comments = commentRepository.findCommentByPost(postOptional.get());
            List<CommentInfo> commentInfos = new ArrayList<>();
            for (Comment comment : comments) {
                CommentInfo commentInfo = new CommentInfo();
                commentInfo.setCommentId(comment.getId());
                commentInfo.setComment(comment.getComment());
                commentInfos.add(commentInfo);
            }
            return PostMappers.mapPostDetails(postOptional.get(), commentInfos);
        }
        throw new PostNotFoundException(APIConstants.POST_NOT_FOUND);
    }

    @Override
    public void deletePost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            try {
                postRepository.deleteById(id);
                return;
            }catch (Exception e) {
                throw new IllegalArgumentException(e.getCause().getCause().getMessage());
            }
        }
        throw new PostNotFoundException(APIConstants.POST_NOT_FOUND);
    }
}
